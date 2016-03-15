/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.psi.comportamente.labgame.relatorios;

import br.ufpa.psi.comportamente.labgame.entidades.Jogada;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 *
 * @author Weslley
 */
public class RelatorioGrafico {

      public RelatorioGrafico() {

      }

    // Gera uma planilha contendo gráfico de barras classificando as jogadas por:
      // acerto individual, acerto cultural e sem acerto.
      public InputStream estatisticaJogadas(List<Jogada> jogadas) throws ParsePropertyException, IOException, InvalidFormatException {

            TipoJogada jogadaPontCult = new TipoJogada("Pontuação Cultural", 0);
            TipoJogada jogadaPontIndiv = new TipoJogada("Pontuação Individual", 0);
            TipoJogada jogadaSemPont = new TipoJogada("Sem Pontuação", 0);

            for (Jogada jogada : jogadas) {
                  int cont = 0;
                  if (jogada.getPontuacaoCultural() > 0) {
                        cont++;
                        jogadaPontCult.total++;
                  }
                  if (jogada.getPontuacaoIndividual() > 0) {
                        cont++;
                        jogadaPontIndiv.total++;
                  }
                  if (cont == 0) {
                        jogadaSemPont.total++;
                  }
            }

            HSSFWorkbook workbook = new HSSFWorkbook();

            List<TipoJogada> listaTipoJogadas = new ArrayList<>();
            listaTipoJogadas.add(jogadaSemPont);
            listaTipoJogadas.add(jogadaPontCult);
            listaTipoJogadas.add(jogadaPontIndiv);

            Map beans = new HashMap();

            beans.put("jogadas", listaTipoJogadas);
            XLSTransformer transformer = new XLSTransformer();

            InputStream ioStream = RelatorioGrafico.class.getClassLoader().getResourceAsStream("br/ufpa/psi/comportamente/labgame/relatorios/templates/Template_Chart_Pontuacao.xls");

            transformer.markAsFixedSizeCollection("jogadas");

            byte[] bytes;
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                  workbook = (HSSFWorkbook) transformer.transformXLS(ioStream, beans);
                  workbook.write(out);
                  bytes = out.toByteArray();
            }

            InputStream in = new ByteArrayInputStream(bytes);

            return in;
      }

    // Classe interna usada pra classificar a jogada pelo tipo de pontuação e informar
      // o número de jogadas que atingiram o mesmo tipo de pontuação.
      public static class TipoJogada {

            private String tipo;
            private int total;

            public TipoJogada(String tipo, int total) {
                  this.tipo = tipo;
                  this.total = total;
            }

            public String getTipo() {
                  return tipo;
            }

            public void setTipo(String tipo) {
                  this.tipo = tipo;
            }

            public int getTotal() {
                  return total;
            }

            public void setTotal(int total) {
                  this.total = total;
            }

      }

}
