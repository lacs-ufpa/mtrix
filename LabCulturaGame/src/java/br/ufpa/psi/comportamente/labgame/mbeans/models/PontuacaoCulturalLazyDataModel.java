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

import br.ufpa.psi.comportamente.labgame.dao.PontuacaoCulturalDAO;
import br.ufpa.psi.comportamente.labgame.entidades.PontuacaoCultural;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Weslley
 */
public class PontuacaoCulturalLazyDataModel extends LazyDataModel<PontuacaoCultural> {

    private PontuacaoCulturalDAO dao;
    private List<PontuacaoCultural> pontuacoes;
    
    @Override
    public List<PontuacaoCultural> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        try {
            try {
                dao = new PontuacaoCulturalDAO();
                dao.beginTransaction();
                pontuacoes = dao.findAll(first, pageSize);
            } finally {
                dao.stopOperation(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (getRowCount() <= 0) {
            dao = new PontuacaoCulturalDAO();
            dao.beginTransaction();
            setRowCount(dao.contaPontuacoesTotal());
            dao.stopOperation(false);
        }

        setPageSize(pageSize);

        return pontuacoes;
    }

    @Override
    public Object getRowKey(PontuacaoCultural pontuacao) {
        return pontuacao.getId();
    }

    @Override
    public PontuacaoCultural getRowData(String rowKey) {        
        for (PontuacaoCultural pontuacao : pontuacoes) {
            if (pontuacao.getId().equals(Long.valueOf(rowKey))) {
                return pontuacao;
            }
        }
        return null;
    }
}
