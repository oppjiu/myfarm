package cn.jxufe.system.db;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;

public class ProcedureCall {

    public static String callProc(EntityManager entityManager, String procedure, ArrayList<Object> params) {
        Query query = entityManager.createNativeQuery(procedure);
        for (int index = 0; index < params.size(); index++)
            query.setParameter(index + 1, params.get(index));
        String result = query.getSingleResult().toString();
        return result;
    }
}
