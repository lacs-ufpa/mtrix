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
package br.ufpa.psi.comportamente.labgame.mbeans.models;

import br.ufpa.psi.comportamente.labgame.dao.ExperimentoDAO;
import br.ufpa.psi.comportamente.labgame.entidades.Experimento;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Weslley
 */
public class ExperimentosLazyDataModel extends LazyDataModel<Experimento> {

      private ExperimentoDAO dao;
      private List<Experimento> experimentos;

      @Override
      public List<Experimento> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            try {
                  try {
                        String filterValue = (String) filters.get("globalFilter");
                        dao = new ExperimentoDAO();
                        dao.beginTransaction();
                        if (filterValue == null || filterValue.isEmpty()) {
                              experimentos = dao.findExperimentos(first, pageSize);
                        } else {
                              experimentos = dao.findExperimentosFiltro(first, pageSize, filterValue);
                        }
                  } finally {
                        dao.stopOperation(false);
                  }
            } catch (Exception e) {
                  e.printStackTrace();
            }

            if (getRowCount() <= 0) {
                  dao = new ExperimentoDAO();
                  dao.beginTransaction();
                  setRowCount(dao.contaExperimentosTotal());
                  dao.stopOperation(false);
            }

            setPageSize(pageSize);

            return experimentos;
      }

      @Override
      public Experimento getRowData(String rowKey) {
            for (Experimento experimento : experimentos) {
                  if (experimento.getId().equals(Long.valueOf(rowKey))) {
                        return experimento;
                  }
            }
            return null;
      }

      @Override
      public Object getRowKey(Experimento experimento) {
            return experimento.getId();
      }

}
