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
package br.ufpa.psi.comportamente.labgame.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author Weslley
 * @param <T>
 */
public class BaseDAO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    //Código signec
    static EntityManagerFactory emf;

    private ThreadLocal<EntityManager> managerThreadLocal;

    private ThreadLocal<EntityTransaction> transactionThreadLocal;

    /**
     * Construtor padr�o. Seta a variavel estatica emf (EntityManagerFactory)
     * caso esteja nula. E caso estejam nulos, seta tamb�m o managerThreadLocal
     * e transactionThreadLocal.
     */
    public BaseDAO() {
        if (emf == null) {
            emf = getEntityManagerFactory();
        }

        if (managerThreadLocal == null) {
            managerThreadLocal = new ThreadLocal<>();
        }

        if (transactionThreadLocal == null) {
            transactionThreadLocal = new ThreadLocal<>();
        }
    }

    /**
     * Retorna um novo EntityManagerFactory
     *
     * @return Persistence.createEntityManagerFactory("mtrix");
     */
    private EntityManagerFactory getEntityManagerFactory() {
        if (emf != null) {
            return emf;
        } else {
            emf = Persistence.createEntityManagerFactory("mtrix");
            return emf;
        }
    }

    /**
     * Retorna um EntityManager, caso a ThreadLocal esteja nula, ele cria um
     * novo.
     *
     * @return EntityManager
     */
    public EntityManager getEntityManager() {
        if (managerThreadLocal.get() == null) {
            EntityManager em = emf.createEntityManager();
            managerThreadLocal.set(em);
        }
        return managerThreadLocal.get();
    }

    /**
     * Retorna um EntityTransaction, caso a ThreadLocal esteja nula, ele cria um
     * novo.
     *
     * @return EntityManager
     */
    public EntityTransaction getEntityTransaction() {
        if (transactionThreadLocal.get() == null) {
            EntityTransaction transaction = getEntityManager().getTransaction();
            transactionThreadLocal.set(transaction);
        }
        return transactionThreadLocal.get();
    }

    /**
     * Inicia a operação com o banco. Caso não tenha um entityTransaction ativo,
     * ele tenta iniciar um novo, podendo disparar uma exceção de persistencia.
     * A excecao eh por falha na conexão. Então, ele tenta se reconectar
     * buscando um novo EntityManagerFactory, novo EntityManager e novo
     * EntityTransaction.
     */
    public void beginTransaction() {

        if (!(getEntityTransaction().isActive())) {
            try {
                getEntityTransaction().begin();
                getEntityManager().clear();
            } catch (PersistenceException p) {

                emf = getEntityManagerFactory();
                EntityManager em = emf.createEntityManager();
                managerThreadLocal.set(em);

                EntityTransaction et = managerThreadLocal.get().getTransaction();
                transactionThreadLocal.set(et);

                getEntityTransaction().begin();
                getEntityManager().clear();
            }
        }

    }

    /**
     * Finaliza a operacao com o banco. Deve receber como parametro true, caso
     * deva ser efetuado o commit, e false caso deva ser efetuado o rollback.
     *
     * @param commitChanges
     */
    public void stopOperation(boolean commitChanges) {

        if (getEntityTransaction().isActive()) {
            if (commitChanges) {
                getEntityManager().flush();
                getEntityTransaction().commit();

            } else {
                getEntityTransaction().rollback();
            }

            getEntityManager().close();
            transactionThreadLocal.remove();
            managerThreadLocal.remove();
        } else {
            beginTransaction();
            if (commitChanges) {
                getEntityManager().flush();
                getEntityTransaction().commit();
            } else {
                getEntityTransaction().rollback();
            }
            getEntityManager().close();
            transactionThreadLocal.remove();
            managerThreadLocal.remove();
        }

    }

    //

    /*
     private static final EntityManagerFactory emf = Persistence
     .createEntityManagerFactory("mtrix");
     private EntityManager em;

     private Class<T> entityClass;
    
    
     public BaseDAO(Class<T> entityClass) {
     this.entityClass = entityClass;
     }
    
     // TODO: Verificar se o problema de várias conexões acontece aqui!
     // Foi criado um getEntityManager aqui nessa classe mas o mesmo ainda não resolveu o problema.
     public void beginTransaction() {
     em = emf.createEntityManager();

     em.getTransaction().begin();
     }
     */
    public void commit() {
        getEntityManager().getTransaction().commit();
    }

    public void rollback() {
        getEntityManager().getTransaction().rollback();
    }

    public void closeTransaction() {
        getEntityManager().close();
    }

    public void commitAndCloseTransaction() {
        commit();
        closeTransaction();
    }

    public void flush() {
        getEntityManager().flush();
    }

    public void save(T entity) {
        getEntityManager().persist(entity);
    }

    public void delete(T entity) {
        T entityToBeRemoved = getEntityManager().merge(entity);

        getEntityManager().remove(entityToBeRemoved);
    }

    public T update(T entity) {
        return getEntityManager().merge(entity);
    }

    public T find(Class<T> classType, Object id) {
        return getEntityManager().find(classType, id);
    }

    public T findReferenceOnly(Class<T> classType, Object id) {
        return getEntityManager().getReference(classType, id);
    }

    // Using the unchecked because JPA does not have a
    // em.getCriteriaBuilder().createQuery()<T> method
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<T> findAll(Class<T> classType) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(classType));
        return getEntityManager().createQuery(cq).getResultList();
    }

    //static ThreadLocal<EntityManager> managerThreadLocal = new ThreadLocal<EntityManager>();
    //static ThreadLocal<EntityTransaction> transactionThreadLocal = new ThreadLocal<EntityTransaction>();
    /**
     * Retorna um EntityManager, caso a ThreadLocal esteja nula, ele cria um
     * novo.
     *
     * @return EntityManager
     */
    /*
     public EntityManager getEntityManager() {
     if (em == null) {
     em = emf.createEntityManager();
     }
     return em;
     }
     */
}
