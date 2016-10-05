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

import br.ufpa.psi.comportamente.labgame.dao.JogadaDAO;
import br.ufpa.psi.comportamente.labgame.entidades.Experimento;
import br.ufpa.psi.comportamente.labgame.entidades.Jogada;
import br.ufpa.psi.comportamente.labgame.entidades.Pesquisador;
import br.ufpa.psi.comportamente.labgame.mbeans.models.ExperimentosRelatorioLazyDataModel;
import br.ufpa.psi.comportamente.labgame.relatorios.RelatorioGrafico;
import br.ufpa.psi.comportamente.labgame.relatorios.RelatorioJogadasExperimento;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import net.sf.jxls.exception.ParsePropertyException;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.StreamedContent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Weslley
 */
@ManagedBean
@ViewScoped
public class RelatoriosMB implements Serializable {

      private static final long serialVersionUID = 1L;

      private Experimento experimentoSelecionado;
      private LazyDataModel<Experimento> experimentosModel;
      private Pesquisador pesquisador;
      private StreamedContent file;

      public RelatoriosMB() {
            experimentoSelecionado = new Experimento();
            experimentosModel = new ExperimentosRelatorioLazyDataModel();
      }

      public void listaExperimentosRelatorio() {

            SecurityContext context = SecurityContextHolder.getContext();

            if (context instanceof SecurityContext) {
                  Authentication authentication = context.getAuthentication();
                  if (authentication instanceof Authentication && authentication.getPrincipal() instanceof Pesquisador) {

                        pesquisador = (Pesquisador) authentication.getPrincipal();
                  }
            }
      }

      public StreamedContent geraRelatorioJogadasExperimento() throws ParsePropertyException, IOException, InvalidFormatException {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Relatório das Jogadas");

            sheet.setColumnWidth(sheet.getFirstRowNum(), (14 * 256) + 200);
            sheet.setColumnWidth(1, (14 * 256) + 200);
            sheet.setColumnWidth(4, (17 * 256) + 200);
            sheet.setColumnWidth(5, (16 * 256) + 200);

            HSSFCellStyle cs1 = workbook.createCellStyle();
            cs1.setDataFormat(HSSFDataFormat.getBuiltinFormat("d-mmm-yy"));

            HSSFCellStyle cs2 = workbook.createCellStyle();
            cs2.setDataFormat(HSSFDataFormat.getBuiltinFormat("h:mm:ss"));

            JogadaDAO jogadaDAO = new JogadaDAO();
            jogadaDAO.beginTransaction();
            List<Jogada> jogadas = jogadaDAO.encontrarPorExperimento(experimentoSelecionado.getId());

            int countRow = 0;
            Row row1 = sheet.createRow(countRow++);
            Cell cell = row1.createCell(0);
            cell.setCellValue("Experimento: " + experimentoSelecionado.getNome());

            Row row = sheet.createRow(countRow++);

            row.createCell(0).setCellValue("Data da Jogada");
            row.createCell(1).setCellValue("Hora da Jogada");
            row.createCell(2).setCellValue("Coluna");
            row.createCell(3).setCellValue("Linha");
            row.createCell(4).setCellValue("Pontuacao Individual");
            row.createCell(5).setCellValue("Pontuacao Coletiva");
            row.createCell(6).setCellValue("Participante");
            row.createCell(7).setCellValue("Condição");

            for (Jogada jogada : jogadas) {

                  Row nrow = sheet.createRow(countRow++);

                  //Data
                  Cell ncell0 = nrow.createCell(0);
                  ncell0.setCellValue(jogada.getMomento());
                  ncell0.setCellStyle(cs1);

                  //Hora
                  Cell ncell1 = nrow.createCell(1);
                  ncell1.setCellValue(jogada.getMomento());
                  ncell1.setCellStyle(cs2);

                  //Coluna
                  Cell ncell2 = nrow.createCell(2);
                  ncell2.setCellValue(jogada.getColunaSelecionada());

                  //Linha
                  Cell ncell3 = nrow.createCell(3);
                  ncell3.setCellValue(jogada.getLinhaSelecionada());

                  //Pontuação Individual
                  Cell ncell4 = nrow.createCell(4);
                  ncell4.setCellValue(jogada.getPontuacaoIndividual());

                  //Pontuação Coletiva
                  Cell ncell5 = nrow.createCell(5);
                  ncell5.setCellValue(jogada.getPontuacaoCultural());

                  //Jogador
                  Cell ncell6 = nrow.createCell(6);
                  ncell6.setCellValue(jogada.getJogador().getNome());

                  //Id da Condição
                  Cell ncell7 = nrow.createCell(7);
                  ncell7.setCellValue(jogada.getIdCondicao());

            }

            jogadaDAO.stopOperation(false);

            byte[] bytes;
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                  workbook.write(out);
                  bytes = out.toByteArray();
            }

            InputStream ioStream = new ByteArrayInputStream(bytes);
            file = new DefaultStreamedContent(ioStream, "application/vnd.ms-excel", "Relatório_Jogadas.xls");
            return file;
      }

      public StreamedContent geraRelatorioGraficoAcerto() {
            file = null;
            JogadaDAO jogadaDAO = new JogadaDAO();
            jogadaDAO.beginTransaction();
            List<Jogada> jogadas = jogadaDAO.encontrarPorExperimento(experimentoSelecionado.getId());
            jogadaDAO.stopOperation(false);

            // Chamada pro método que irá gerar planilha com gráfico
            RelatorioGrafico grafico = new RelatorioGrafico();

            try {
                  InputStream ioStream = grafico.estatisticaJogadas(jogadas);
                  file = new DefaultStreamedContent(ioStream, "application/vnd.ms-excel", "Relatório_Acertos_por_Jogada.xls");
                  return file;
            } catch (ParsePropertyException | IOException | InvalidFormatException ex) {
                  Logger.getLogger(RelatoriosMB.class.getName()).log(Level.SEVERE, null, ex);
                  return null;
            }

      }

      public StreamedContent geraRelatorioOntogenese() {
            file = null;

            // Chamada pro método que irá gerar planilha com gráfico
            RelatorioJogadasExperimento relatorio = new RelatorioJogadasExperimento();

            try {
                  InputStream ioStream = relatorio.relatorioOntogenese(experimentoSelecionado.getId());
                  file = new DefaultStreamedContent(ioStream, "application/vnd.ms-excel", "Relatório Ontogênese - Experimento" + experimentoSelecionado.getNome() + ".xlsx");
                  return file;
            } catch (ParsePropertyException | IOException ex) {
                  Logger.getLogger(RelatoriosMB.class.getName()).log(Level.SEVERE, null, ex);
                  return null;
            }
      }

      public Experimento getExperimentoSelecionado() {
            return experimentoSelecionado;
      }

      public void setExperimentoSelecionado(Experimento experimentoSelecionado) {
            this.experimentoSelecionado = experimentoSelecionado;
      }

      public Pesquisador getPesquisador() {
            return pesquisador;
      }

      public void setPesquisador(Pesquisador pesquisador) {
            this.pesquisador = pesquisador;
      }

      public StreamedContent getFile() {
            return file;
      }

      public LazyDataModel<Experimento> getExperimentosModel() {
            return experimentosModel;
      }

      public void setExperimentosModel(LazyDataModel<Experimento> experimentosModel) {
            this.experimentosModel = experimentosModel;
      }

}
