
import aed3.*;
import java.util.ArrayList;

public class ArquivoEpisodio extends Arquivo<Episodio> {

    Arquivo<Episodio> arqEpisodios;
    ArquivoSerie arqSerie;
    HashExtensivel<ParNomeEpisodioID> indiceIndiretoNomeEpisodio;
    ArvoreBMais<ParIDSerieIDEpisodio> indiceIndiretoIDSerieIDEpisodio;

    public ArquivoEpisodio() throws Exception {
        super("episodios", Episodio.class.getConstructor());

        arqSerie = new ArquivoSerie();

        indiceIndiretoNomeEpisodio = new HashExtensivel<>(
                ParNomeEpisodioID.class.getConstructor(),
                4,
                ".\\dados\\episodios\\indiceNomeEpisodio.d.db",
                ".\\dados\\episodios\\indiceNomeEpisodio.c.db"
        );

        indiceIndiretoIDSerieIDEpisodio = new ArvoreBMais<>(
                ParIDSerieIDEpisodio.class.getConstructor(),
                5,
                ".\\dados\\episodios\\indiceIndiretoIDSerieIDEpisodio.db"
        );
    }

    @Override
    public int create(Episodio ep) throws Exception {
        int id = super.create(ep);
        String serieNome = ep.getNome();
        ParNomeEpisodioID pneid = new ParNomeEpisodioID(serieNome, id);
        indiceIndiretoNomeEpisodio.create(pneid);
        indiceIndiretoIDSerieIDEpisodio.create(new ParIDSerieIDEpisodio(ep.idSerie, ep.id));
        return id;
    }

    public Episodio readNome(String nome) throws Exception {
        ParNomeEpisodioID pni = indiceIndiretoNomeEpisodio.read(ParNomeEpisodioID.hash(nome));
        if (pni == null) {
            return null;
        }
        return read(pni.getId());
    }

    public ArrayList<Episodio> readSerie(String nome) throws Exception {
        if (nome == null) {
            return null;
        }
        int serieID = arqSerie.read(nome).id;
        ParIDSerieIDEpisodio key = new ParIDSerieIDEpisodio(serieID, -1);
        ArrayList<ParIDSerieIDEpisodio> pse = indiceIndiretoIDSerieIDEpisodio.read(key);
        ArrayList<Episodio> episodios = new ArrayList<>();
        ParIDSerieIDEpisodio pseaux = null;

        if (pse.size() > 0) {
            for (int i = 0; i < pse.size(); i++) {
                pseaux = pse.get(i);

                if (pseaux != null) {
                    pseaux = pse.get(i);
                    episodios.add(read(pseaux.getIDEpisodio()));
                } else {
                    System.out.println("ArquivoEpisodio.readSerie: erro na recuperacao de episodio");
                }
            }
            return episodios;
        } else {
            return null;
        }

    }

    public boolean delete(String nome, int IDSerie) throws Exception {
        ParNomeEpisodioID pni = indiceIndiretoNomeEpisodio.read(ParNomeEpisodioID.hash(nome));
        if (pni == null) {
            return false;
        }
        int epID = pni.getId();
        if (!super.delete(epID)) {
            return false;
        }
        boolean ok1 = indiceIndiretoNomeEpisodio.delete(ParNomeEpisodioID.hash(nome));
        boolean ok2 = indiceIndiretoIDSerieIDEpisodio.delete(
                new ParIDSerieIDEpisodio(IDSerie, epID)
        );
        return ok1 && ok2;
    }

    public boolean update(Episodio novoEpisodio, String nomeAntigo) throws Exception {
        Episodio episodioAntigo = readNome(nomeAntigo);
        if (episodioAntigo == null) {
            return false; // não há o que atualizar
        }

        boolean atualizado = super.update(novoEpisodio);
        if (!atualizado) {
            return false;
        }

        if (!novoEpisodio.getNome().equals(nomeAntigo)) {
            indiceIndiretoNomeEpisodio.delete(ParNomeEpisodioID.hash(nomeAntigo));
            indiceIndiretoNomeEpisodio.create(
                    new ParNomeEpisodioID(novoEpisodio.getNome(), novoEpisodio.getID())
            );
        }

        if (novoEpisodio.getIdSerie() != episodioAntigo.getIdSerie()) {
            indiceIndiretoIDSerieIDEpisodio.delete(
                    new ParIDSerieIDEpisodio(episodioAntigo.getIdSerie(), episodioAntigo.getID())
            );
            indiceIndiretoIDSerieIDEpisodio.create(
                    new ParIDSerieIDEpisodio(novoEpisodio.getIdSerie(), novoEpisodio.getID())
            );
        }

        return true;
    }

}
