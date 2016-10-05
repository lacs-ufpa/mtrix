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
package br.ufpa.psi.comportamente.labgame.relatorios;

import br.ufpa.psi.comportamente.labgame.dao.ExperimentoDAO;
import br.ufpa.psi.comportamente.labgame.dao.JogadaDAO;
import br.ufpa.psi.comportamente.labgame.dao.JogadorDAO;
import br.ufpa.psi.comportamente.labgame.entidades.Experimento;
import br.ufpa.psi.comportamente.labgame.entidades.Jogada;
import br.ufpa.psi.comportamente.labgame.entidades.Jogador;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Weslley
 */
public class RelatorioJogadasExperimento {
      
      public RelatorioJogadasExperimento() {
            
      }

      public InputStream relatorioOntogenese(Long idExp) throws FileNotFoundException, IOException {
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Relatório Ontogênese");

            JogadaDAO jogadaDAO = new JogadaDAO();

            jogadaDAO.beginTransaction();
            List<Jogada> jogadasAux = jogadaDAO.encontrarPorExperimento(idExp);
            jogadaDAO.stopOperation(false);

            ExperimentoDAO expDAO = new ExperimentoDAO();

            expDAO.beginTransaction();
            Experimento experimento = expDAO.find(Experimento.class, idExp);
            expDAO.stopOperation(false);

            JogadorDAO jogDAO = new JogadorDAO();

            jogDAO.beginTransaction();
            List<Jogador> jogadoresTotais = jogDAO.encontraPorExperimento(experimento);
            jogDAO.stopOperation(false);

            //CRIA O CABEÇALHO "ONTOGÊNESE
            int quantidadeColunas = 6;
            int quantidadeJogadoresPorCiclo = experimento.getTamanhoFilaJogadores(); //Vai pegar o valor de acordo com o experimento.
            int larguraColuna = 4600;
            int colunaAtual;
            int colunaFinalOntogenese = (quantidadeColunas * quantidadeJogadoresPorCiclo) + 1;
            
            // --- DEFINE AS PROPRIEDADES DAS CÉLULAS
            sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(0, 2, 1, 1));
            sheet.setColumnWidth(0, 1500);
            sheet.setColumnWidth(1, 2000);
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, colunaFinalOntogenese));
            sheet.addMergedRegion(new CellRangeAddress(1, 2, colunaFinalOntogenese + 1, colunaFinalOntogenese + 1));
            sheet.setColumnWidth(colunaFinalOntogenese + 1, 3000);
            sheet.addMergedRegion(new CellRangeAddress(1, 3, colunaFinalOntogenese + 2, colunaFinalOntogenese + 2));
            sheet.setColumnWidth(colunaFinalOntogenese + 2, 4000);
            
            // --- FIM

            //for (int i = 0; i < quantidadeJogadoresPorCiclo; i++)
            //if(i == 1){
            sheet.setColumnWidth(2, larguraColuna); // Campo com o nome do participante fica nessa coluna.
            colunaAtual = 2 + quantidadeColunas;
            //} else {
            sheet.setColumnWidth((colunaAtual), larguraColuna);
            colunaAtual += quantidadeColunas;
            sheet.setColumnWidth(colunaAtual, larguraColuna);

            //}
            // ---DEFINE AS CORES DE CADA FONTE
            XSSFFont fonteBranca = (XSSFFont) wb.createFont();
            fonteBranca.setColor(new XSSFColor(Color.WHITE));
            XSSFFont fonteNegra = (XSSFFont) wb.createFont();
            fonteNegra.setColor(new XSSFColor(Color.BLACK));
            XSSFFont fonteVermelha = (XSSFFont) wb.createFont();
            fonteVermelha.setColor(new XSSFColor(Color.RED));
            
            // --- FIM

            // --- DEFINE ESTILOS CÉLULAS
            //ESTILO COLUNA LINHA
            XSSFCellStyle estiloColunaLinha = (XSSFCellStyle) wb.createCellStyle();

            estiloColunaLinha.setVerticalAlignment(VerticalAlignment.CENTER);
            estiloColunaLinha.setAlignment(CellStyle.ALIGN_CENTER);
            estiloColunaLinha.setFillForegroundColor(new XSSFColor(Color.LIGHT_GRAY));
            estiloColunaLinha.setFillPattern(CellStyle.SOLID_FOREGROUND);
            estiloColunaLinha.getFont().setBold(true);
            estiloColunaLinha.setBorderBottom(BorderStyle.MEDIUM);
            estiloColunaLinha.setBorderLeft(BorderStyle.MEDIUM);
            estiloColunaLinha.setBorderRight(BorderStyle.MEDIUM);
            estiloColunaLinha.setBorderTop(BorderStyle.MEDIUM);

            XSSFCellStyle estiloColunaColuna = (XSSFCellStyle) wb.createCellStyle();

            estiloColunaColuna.setVerticalAlignment(VerticalAlignment.CENTER);
            estiloColunaColuna.setAlignment(CellStyle.ALIGN_CENTER);
            estiloColunaColuna.getFont().setBold(true);

            //ESTILO CABEÇALHO
            XSSFCellStyle estiloCabecalhoColunaAB = (XSSFCellStyle) wb.createCellStyle();

            estiloCabecalhoColunaAB.setVerticalAlignment(VerticalAlignment.CENTER);
            estiloCabecalhoColunaAB.setAlignment(CellStyle.ALIGN_CENTER);
            estiloCabecalhoColunaAB.setFillForegroundColor(new XSSFColor(Color.LIGHT_GRAY));
            estiloCabecalhoColunaAB.setFillPattern(CellStyle.SOLID_FOREGROUND);
            estiloCabecalhoColunaAB.getFont().setBold(true);

            XSSFCellStyle estiloCabecalhoColunaP = (XSSFCellStyle) wb.createCellStyle();

            estiloCabecalhoColunaP.setVerticalAlignment(VerticalAlignment.CENTER);
            estiloCabecalhoColunaP.setAlignment(CellStyle.ALIGN_CENTER);
            estiloCabecalhoColunaP.setFont(fonteNegra);
            estiloCabecalhoColunaP.getFont().setBold(true);

            //ESTILO MUDANÇA DE CICLO
            XSSFCellStyle estiloMudancaCiclo = (XSSFCellStyle) wb.createCellStyle();
            estiloMudancaCiclo.setVerticalAlignment(VerticalAlignment.CENTER);
            estiloMudancaCiclo.setAlignment(CellStyle.ALIGN_CENTER);
            estiloMudancaCiclo.setFillForegroundColor(new XSSFColor(Color.RED));
            estiloMudancaCiclo.setFillPattern(CellStyle.SOLID_FOREGROUND);
            estiloMudancaCiclo.setFont(fonteBranca);

            //ESTILO CICLO SEM MUDANÇA
            XSSFCellStyle estiloCicloSemMudanca = (XSSFCellStyle) wb.createCellStyle();
            estiloCicloSemMudanca.setVerticalAlignment(VerticalAlignment.CENTER);
            estiloCicloSemMudanca.setAlignment(CellStyle.ALIGN_CENTER);
            estiloCicloSemMudanca.setFont(fonteNegra);

            //ESTILO NOME PARTICIPANTE
            XSSFCellStyle estiloNomeP = (XSSFCellStyle) wb.createCellStyle();
            estiloNomeP.setVerticalAlignment(VerticalAlignment.CENTER);
            estiloNomeP.setAlignment(CellStyle.ALIGN_CENTER);
            estiloNomeP.setBorderBottom(BorderStyle.DOTTED);
            estiloNomeP.setBorderTop(BorderStyle.DOTTED);
            estiloNomeP.setFillForegroundColor(new XSSFColor(Color.BLACK));
            estiloNomeP.setFillPattern(CellStyle.SOLID_FOREGROUND);
            estiloNomeP.setFont(fonteBranca);
            estiloNomeP.getFont().setBold(true);
            
            //ESTILO ESTABILIDADE
            XSSFCellStyle estiloEstabilidade = (XSSFCellStyle) wb.createCellStyle();
            estiloEstabilidade.setVerticalAlignment(VerticalAlignment.CENTER);
            estiloEstabilidade.setAlignment(CellStyle.ALIGN_CENTER);
            estiloEstabilidade.setFont(fonteVermelha);
            estiloEstabilidade.getFont().setBold(true);
            
            // --- FIM

            int cr = 0;

            // --- CRIA PRIMEIRA COLUNA (CABEÇALHO)
            XSSFRow row1 = (XSSFRow) sheet.createRow((short) cr++);

            //CRIA CÉLULA 1
            XSSFCell c1 = row1.createCell(0);

            c1.setCellValue("FASE");
            c1.setCellStyle(estiloCabecalhoColunaAB);

            //CRIA CÉLULA 2
            XSSFCell c2 = row1.createCell(1);

            c2.setCellValue("CICLO");
            c2.setCellStyle(estiloCabecalhoColunaAB);

            //CRIA CÉLULA 3 (ONTOGÊNESE)
            XSSFCell cOnto = row1.createCell(2);
            cOnto.setCellValue("ONTOGÊNESE");
            cOnto.setCellStyle(estiloCicloSemMudanca);

            //CRIA CÉLULA 'CC'
            XSSFRow row2 = (XSSFRow) sheet.createRow(1);
            XSSFCell cCC = row2.createCell(colunaFinalOntogenese + 1);
            cCC.setCellValue("CC");
            cCC.setCellStyle(estiloCicloSemMudanca);
            
            //CRIA CÉLULA 'ESTABILIDADE'
            XSSFCell cEstab = row2.createCell(colunaFinalOntogenese + 2);
            cEstab.setCellValue("ESTABILIDADE");
            cEstab.setCellStyle(estiloEstabilidade);
            
            // --- FIM

            int contadorCelulasCabecalho = 2;
            int contadorAcertosCultural = 0;
            XSSFRow row3 = (XSSFRow) sheet.createRow((short) 2);
            //GERA O CABEÇALHO DAS JOGADAS
            for (int i = 0; i < experimento.getTamanhoFilaJogadores(); i++) {
                  //CRIA CÉLULA 3 NA LINHA 3
                  XSSFCell c3 = row3.createCell(contadorCelulasCabecalho++);

                  c3.setCellValue("P");
                  c3.setCellStyle(estiloCabecalhoColunaP);

                  //CRIA CÉLULA 4 NA LINHA 3
                  XSSFCell c4 = row3.createCell(contadorCelulasCabecalho++);

                  c4.setCellValue("Linha");
                  c4.setCellStyle(estiloCabecalhoColunaP);

                  //CRIA CÉLULA 5 NA LINHA 3
                  XSSFCell c5 = row3.createCell(contadorCelulasCabecalho++);

                  c5.setCellValue("Cor");
                  c5.setCellStyle(estiloCabecalhoColunaP);

                  //CRIA CÉLULA 6 NA LINHA 3
                  XSSFCell c6 = row3.createCell(contadorCelulasCabecalho++);

                  c6.setCellValue("Col");
                  c6.setCellStyle(estiloCabecalhoColunaP);

                  //CRIA CÉLULA 7 NA LINHA 3
                  XSSFCell c7 = row3.createCell(contadorCelulasCabecalho++);

                  c7.setCellValue("CI");
                  c7.setCellStyle(estiloCabecalhoColunaP);

                  //CRIA CÉLULA 8 NA LINHA 3
                  XSSFCell c8 = row3.createCell(contadorCelulasCabecalho++);

                  c8.setCellValue("CI Cum");
                  c8.setCellStyle(estiloCabecalhoColunaP);

            }

            //VARIÁVEIS INICIAIS DA ELABORAÇÃO DO RELATÓRIO
            int quantidadeCiclosTotais = (jogadasAux.size() / experimento.getTamanhoFilaJogadores());
            int contRowJogadas = 4;
            int contadorCiclo = 1;

            //INICIA LISTA DOS JOGADORES POR ORDEM
            List<Jogador> jogadoresAtuais = new ArrayList<>();
            int contOrdem = 1;
            for (int i = 0; i < quantidadeJogadoresPorCiclo; i++) {
                  for (Jogador jgdr : jogadoresTotais) {
                        if (jgdr.getOrdem() == contOrdem) {
                              jogadoresAtuais.add(jgdr);
                              contOrdem++;
                              break;
                        }
                  }
            }

            //FAZ A REMOÇÃO DO(S) ELEMENTO(S) DA LISTA PRA POUPAR RECURSO EM BUSCA FUTURA
            jogadoresTotais.removeAll(jogadoresAtuais);

            //CRIA INSTÂNCIA CONTROLE DE PONTUAÇÃO CULTURAL
            int pontCulturalCiclo;

            //FOR (JOGADOR : JOGADORES POR CICLO)
            //CRIA LISTA DE CADA JOGADOR ATÉ FIM DO CICLO
            for (int i = 0; i < quantidadeCiclosTotais; i++) {
                  //PEGA JOGADAS DO CICLO
                  List<Jogada> jogadasCiclo = new ArrayList<>();
                  for (Jogada jogada : jogadasAux) {
                        if (jogada.getRodada() == i + 1) {
                              jogadasCiclo.add(jogada);
                              if (jogadasCiclo.size() == quantidadeJogadoresPorCiclo) {
                                    break;
                              }
                        }
                  }
                  //FAZ A REMOÇÃO DO(S) ELEMENTO(S) DA LISTA PRA POUPAR RECURSO EM BUSCA FUTURA
                  jogadasAux.removeAll(jogadasCiclo);

                  //VERIFICA SE A ORDEM MUDOU
                  int contJogadoresIguais = 0;
                  List<Jogada> jogadasARemover = new ArrayList<>();
                  for (Jogador jogador : jogadoresAtuais) {
                        for (Jogada jogada : jogadasCiclo) {
                              if (jogada.getJogador().compareTo(jogador) == 0) {
                                    jogador.setUltimaJogada(jogada);
                                    jogador.incrementaPontuacaoRelatorio();
                                    jogadasARemover.add(jogada);
                                    contJogadoresIguais++;
                              }
                        }
                  }

                  jogadasCiclo.removeAll(jogadasARemover);

                  boolean mudouGeracaoCiclo = false;
                  if (contJogadoresIguais == quantidadeJogadoresPorCiclo) {
                        //CONTINUA COM OS MESMOS JOGADORES
                  } else {
                        mudouGeracaoCiclo = true;
                        Jogador jogadorARemover = new Jogador();
                        jogadoresAtuais.remove(0);
                        for (Jogador jgdr : jogadoresTotais) {
                              if (jgdr.getOrdem() == contOrdem) {
                                    //PEGA A PRIMEIRA POSIÇÃO PQ ESSA TEM QUE SER A ÚNICA COM ELEMENTO
                                    jgdr.setUltimaJogada(jogadasCiclo.get(0));
                                    jgdr.incrementaPontuacaoRelatorio();
                                    jogadoresAtuais.add(jgdr);
                                    contOrdem++;
                                    jogadorARemover = jgdr;
                                    break;
                              }
                        }
                        jogadoresTotais.remove(jogadorARemover);
                  }
                  // --- ENCERRA ETAPAS DE VERIFICAÇÃO, INICIA A POPULAÇÃO DE NOVA LINHA DO XLSX E
                  //VERIFICA SE EXISTE PONTUAÇÃO CULTURAL.
                  int contCellJogadas = 1;
                  if (jogadoresAtuais.get(0).getUltimaJogada().getPontuacaoCultural() != 0) {
                        pontCulturalCiclo = jogadoresAtuais.get(0).getUltimaJogada().getPontuacaoCultural();
                        contadorAcertosCultural++;
                  } else {
                        pontCulturalCiclo = 0;
                  }

                  XSSFRow row = (XSSFRow) sheet.createRow((short) contRowJogadas);
                  XSSFCell cell = row.createCell(contCellJogadas++);
                  //CICLO
                  if (contRowJogadas == 4 || mudouGeracaoCiclo == true) {
                        //QUANDO HOUVER MUDANÇA DA GERAÇÃO
                        cell.setCellValue(contadorCiclo++);
                        cell.setCellStyle(estiloMudancaCiclo);
                  } else {
                        cell.setCellValue(contadorCiclo++);
                        cell.setCellStyle(estiloCicloSemMudanca);
                  }

                  for (int j = 0; j < quantidadeJogadoresPorCiclo; j++) {

                        //P
                        XSSFCell cell1 = row.createCell(contCellJogadas++);
                        cell1.setCellValue(jogadoresAtuais.get(j).getNome());
                        cell1.setCellStyle(estiloNomeP);

                        //Linha
                        XSSFCell cell2 = row.createCell(contCellJogadas++);
                        cell2.setCellValue(jogadoresAtuais.get(j).getUltimaJogada().getLinhaSelecionada());
                        cell2.setCellStyle(estiloColunaLinha);

                        //Cor
                        XSSFCell cell3 = row.createCell(contCellJogadas++);
                        cell3.setCellValue(jogadoresAtuais.get(j).getUltimaJogada().getCorSelecionada());
                        cell3.setCellStyle(EstiloCelula.retornaEstilo(jogadoresAtuais.get(j).getUltimaJogada().getCorSelecionada(), wb,
                                fonteBranca, fonteNegra));

                        //Col
                        XSSFCell cell4 = row.createCell(contCellJogadas++);
                        cell4.setCellValue(jogadoresAtuais.get(j).getUltimaJogada().getColunaSelecionada());
                        cell4.setCellStyle(estiloColunaColuna);

                        //CI
                        XSSFCell cell5 = row.createCell(contCellJogadas++);
                        cell5.setCellValue(jogadoresAtuais.get(j).getUltimaJogada().getPontuacaoIndividual());
                        cell5.setCellStyle(estiloColunaColuna);

                        //CI Cum
                        XSSFCell cell6 = row.createCell(contCellJogadas++);
                        cell6.setCellValue(jogadoresAtuais.get(j).getPontuacaoExibidaRelatorio());
                        cell6.setCellStyle(estiloColunaColuna);
                  }
                  //CC
                  XSSFCell cell7 = row.createCell(contCellJogadas++);
                  cell7.setCellValue(pontCulturalCiclo);
                  cell7.setCellStyle(estiloCabecalhoColunaAB);
                  
                  // ESTABILIDADE
                  XSSFCell cell8 = row.createCell(contCellJogadas);
                  cell8.setCellValue((contadorAcertosCultural * 100)/(i+1) + "%");
                  cell8.setCellStyle(estiloColunaColuna);
                  
                  contRowJogadas++;
            }

            //ESCREVE O ARQUIVO
            byte[] bytes;
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                  wb.write(out);
                  bytes = out.toByteArray();
            }
            return new ByteArrayInputStream(bytes);
      }
      /*
       public InputStream constroiRelatorioJogadas(List<Jogada> jogadas) {
                
       }
       */

}

class EstiloCelula {

      public static CellStyle retornaEstilo(String cor, Workbook wb, XSSFFont fb, XSSFFont fn) {
            XSSFCellStyle estilo = (XSSFCellStyle) wb.createCellStyle();

            estilo.setVerticalAlignment(VerticalAlignment.CENTER);
            estilo.setAlignment(CellStyle.ALIGN_CENTER);
            estilo.setFillPattern(CellStyle.SOLID_FOREGROUND);
            estilo.setBorderBottom(BorderStyle.MEDIUM);
            estilo.setBorderTop(BorderStyle.MEDIUM);
            estilo.setFont(fb);
            estilo.getFont().setBold(true);
            switch (cor) {
                  case "verde":
                        estilo.setFillForegroundColor(new XSSFColor(new java.awt.Color(31, 131, 101)));
                        return estilo;
                  case "azul":
                        estilo.setFillForegroundColor(new XSSFColor(Color.BLUE));
                        return estilo;
                  case "vermelho":
                        estilo.setFillForegroundColor(new XSSFColor(Color.RED));
                        return estilo;
                  case "amarelo":
                        estilo.setFillForegroundColor(new XSSFColor(Color.YELLOW));
                        estilo.setFont(fn);
                        return estilo;
                  case "roxo":
                        estilo.setFillForegroundColor(new XSSFColor(new java.awt.Color(128, 0, 128)));
                        return estilo;
                  default:
                        return estilo;
            }
      }

}
