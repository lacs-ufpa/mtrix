/*~
M-Trix - Social Experiments Application.
Copyright (C) 2015-2016 The M-Trix authors.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
~*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.psi.comportamente.labgame.mbeans;

import br.ufpa.psi.comportamente.labgame.dao.PremioDAO;
import br.ufpa.psi.comportamente.labgame.entidades.Premio;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Weslley
 */
@ManagedBean
@SessionScoped
public class PremioMB implements Serializable {

    private static final long serialVersionUID = 1L;

    private Premio premio;
    private PremioDAO dao;
    private List<Premio> premios;

    private static final ServletContext context = (ServletContext) FacesContext
            .getCurrentInstance().getExternalContext().getContext();
    private static final String fullPath = context.getRealPath("/WEB-INF/premios/");
    private static final String DIRETORIO_ARQUIVO = fullPath;

    public PremioMB() {
        premio = new Premio();
    }
    /*
     public void uploadImagemPremio(FileUploadEvent event) {
     UploadedFile uf = event.getFile();
     String nomeArquivo = uf.getFileName();
     File f = new File(DIRETORIO_ARQUIVO + nomeArquivo);
        
     premio.setCaminho(f.getPath());

     FileOutputStream os = null;
     InputStream is = null;
     try {
     is = uf.getInputstream();
     byte[] b = new byte[is.available()];
     os = new FileOutputStream(f.getAbsolutePath());
     while (is.read(b) > 0) {
     os.write(b);
     }
     os.flush();
     os.close();
     is.close();
     } catch (IOException ex) {
     Logger.getLogger(PremioMB.class.getName()).log(Level.SEVERE, null, ex);
     }
     }
     */

    public void criaArquivo(byte[] bytes, String arquivo) {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(arquivo);
            fos.write(bytes);
            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PremioMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PremioMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void uploadImagemPremio(FileUploadEvent event) throws IOException {
        byte[] img = event.getFile().getContents();
        String imagemTemporaria = event.getFile().getFileName();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
        String arquivo = scontext.getRealPath("/WEB-INF/premios/" + imagemTemporaria);

        premio.setCaminho(arquivo);

        criaArquivo(img, arquivo);
        StreamedContent imagemEnviada = new DefaultStreamedContent(event.getFile().getInputstream());
    }

    public void cadastraPremio() {
        dao = new PremioDAO();
        dao.beginTransaction();
        if (dao.verificaTipoIgual(premio.getTipo()) || premio.getCaminho().equalsIgnoreCase("") || null == premio.getCaminho()) {
            dao.stopOperation(false);

            dao = new PremioDAO();
            dao.beginTransaction();
            dao.save(premio);
            dao.stopOperation(true);

            premio = new Premio();

            listaPremios();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo de premiação cadastrada."));
        } else {
            dao.stopOperation(false);
            premio = new Premio();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Desculpe mas já existe esse tipo cadastrado. Tente outro"));
        }
    }

    public void listaPremios() {
        premios = new ArrayList<>();
        dao = new PremioDAO();
        dao.beginTransaction();
        premios = dao.findAll(Premio.class);
        dao.stopOperation(false);
    }

    public StreamedContent getImagem() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String premioId = context.getExternalContext().getRequestParameterMap().get("premioId");
            Long id = Long.parseLong(premioId);
            PremioDAO daoAux = new PremioDAO();
            daoAux.beginTransaction();
            Premio premioAux = daoAux.find(Premio.class, id);
            daoAux.stopOperation(false);
            File f = new File(premioAux.getCaminho());
            byte[] fContent = Files.readAllBytes(f.toPath());
            return new DefaultStreamedContent(new ByteArrayInputStream(fContent), "image/jpg");
        }
    }

    

    public Premio getPremio() {
        return premio;
    }

    public void setPremio(Premio premio) {
        this.premio = premio;
    }

    public List<Premio> getPremios() {
        return premios;
    }

    public void setPremios(List<Premio> premios) {
        this.premios = premios;
    }

}
