package db;

import Entity.Balance;
import Entity.Rates;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by admin on 22.03.2018.
 */
public class DB {

    public int findValue(String nameVal) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT B FROM Balance B where B.namevalue=:name");
        query.setParameter("name",nameVal);
        List<Balance> result = query.list();
        session.getTransaction().commit();
        if(!result.isEmpty()){
            return result.get(0).getValue();
        }else return -1;
    }

    public String readFormul(String codeFormula){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT R FROM Rates R where R.code=:name");
        query.setParameter("name",codeFormula);
        List<Rates> result = query.list();
        session.getTransaction().commit();
        if(!result.isEmpty()){
            return result.get(0).getFormul();
        }else return null;
    }

    public List<Rates> readAllFormuls(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT R FROM Rates R where R.id>:name");
        query.setParameter("name",0);
        List<Rates> result = query.list();
        session.getTransaction().commit();
        return result;
    }

    public Rates getRatesByCode(String code){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT R FROM Rates R where R.code=:name");
        query.setParameter("name",code);
        List<Rates> result = query.list();
        session.getTransaction().commit();
        return result.get(0);
    }

    public void addBalanceValue(String nameValue,int value){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Balance balance = new Balance();
        balance.setNamevalue(nameValue);
        balance.setValue(value);
        session.beginTransaction();
        session.save(balance);
        session.getTransaction().commit();
    }

    public void addRates(String code,String formul){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Rates rates = new Rates();
        rates.setCode(code);
        rates.setFormul(formul);
        session.beginTransaction();
        session.save(rates);
        session.getTransaction().commit();
    }
}
