package com.thegamedevdude;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.AbstractQuery;

public class DataBaseAccess {
    public static long enterQuestionIntoDatabase(Question question) {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("question_hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        session.save(question);
        long questionId = question.getQuestion_id();
        t.commit(); 
        factory.close();
        session.close();
        return questionId;
    }

    public static void enterOptionIntoDatabase(List<String> options, long questionId) {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("option_hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        options.forEach(o -> {
            Option option = new Option();
            option.setValue(o);
            option.setQuestion_id(questionId);
            session.save(option);
        });
        t.commit(); 
        factory.close();
        session.close();
    }

    public static List<Question> getListQuestions(EntityManagerFactory emf) {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("question_hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        factory.close();
        session.close();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Question> cq = cb.createQuery(Question.class);
        Root<Question> quest = cq.from(Question.class);
        cq.select(quest.get("question_id"));
        CriteriaQuery<Question> select = cq.select(quest);
        TypedQuery<Question> q = em.createQuery(select);
        List<Question> list = q.getResultList();
        em.getTransaction().commit();
        em.close();
        emf.close();
        return list;
    }

    public static void enterUserInDataBase(User user) {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("user_hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        session.save(user);
        t.commit();
        factory.close();
        session.close();
    }

    public static User getUserFromDataBase(EntityManagerFactory emf, User user) {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("user_hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        factory.close();
        session.close();
        
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> getUser = cq.from(User.class);
        cq.where(cb.equal(getUser.get("username"), user.getUsername()), cb.equal(getUser.get("password"), user.getPassword()));
        CriteriaQuery<User> select = cq.select(getUser);
        TypedQuery<User> tq = em.createQuery(select);
        List<User> list = tq.getResultList();
        em.getTransaction().commit();
        em.clear();
        emf.close();
        if(list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    public static User getUserFromDataBase(EntityManagerFactory emf, String username) {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("user_hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        factory.close();
        session.close();

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> getUser = cq.from(User.class);
        cq.where(cb.equal(getUser.get("username"), username));
        CriteriaQuery<User> select = cq.select(getUser);
        TypedQuery<User> tq = em.createQuery(select);
        List<User> list = tq.getResultList();
        em.getTransaction().commit();
        em.clear();
        emf.close();

        if(list.size() == 0) {
            return null;
        }

        return list.get(0);
    }

    public static Question getQuestionFromDataBase(EntityManagerFactory emf, long questionId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Question> cq = cb.createQuery(Question.class);
        Root<Question> getQuestion = cq.from(Question.class);
        cq.where(cb.equal(getQuestion.get("question_id"), questionId));
        CriteriaQuery<Question> select = cq.select(getQuestion);
        TypedQuery<Question> tq = em.createQuery(select);
        List<Question> list = tq.getResultList();
        em.getTransaction().commit();
        em.clear();
        emf.close();
        return list.get(0);
    }

    public static List<Option> getOptionsFromDataBase(EntityManagerFactory emf, long questionId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Option> cq = cb.createQuery(Option.class);
        Root<Option> getOption = cq.from(Option.class);
        cq.where(cb.equal(getOption.get("question_id"), questionId));
        CriteriaQuery<Option> select = cq.select(getOption);
        TypedQuery<Option> tq = em.createQuery(select);
        List<Option> list = tq.getResultList();
        em.getTransaction().commit();
        em.clear();
        em.close();
        emf.close();
        return list;
    }

    public static void enterVotesIntoDataBase(Vote vote) {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("vote_hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        session.save(vote);
        t.commit();
        factory.close();
        session.close();
    }

    public static List<Vote> getVoteFromDataBase(EntityManagerFactory emf,long userId, long questionId) {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("vote_hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        factory.close();
        session.close();

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Vote> cq = cb.createQuery(Vote.class);
        Root<Vote> getVote = cq.from(Vote.class);
        
        cq.where(cb.equal(getVote.get("question_id"), questionId), cb.equal(getVote.get("user_id"), userId));
        CriteriaQuery<Vote> select = cq.select(getVote);
        TypedQuery<Vote> tq = em.createQuery(select);
        List<Vote> list = tq.getResultList();

        em.getTransaction().commit();
        em.clear();
        em.close();
        emf.close();
        return list;
    }

    public static List<Integer> getVotesForEachOptionFromDataBase(EntityManagerFactory emf, List<Option> options) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Vote> cq = cb.createQuery(Vote.class);
        Root<Vote> getVote = cq.from(Vote.class);

        List<Integer> votesForEach = new ArrayList<Integer>();

        for(Option option : options) {            
            cq.where(cb.equal(getVote.get("option_id"), option.getOption_id()));
            CriteriaQuery<Vote> select = cq.select(getVote);
            TypedQuery<Vote> tq = em.createQuery(select);
            List<Vote> list = tq.getResultList();
            votesForEach.add(list.size());
        }

        int totalVotes = votesForEach.stream()
            .reduce(0, (a, b) -> a + b);

            try {
                votesForEach = votesForEach.stream()
                    .map(e -> (e * 100) / totalVotes)
                    .collect(Collectors.toCollection(ArrayList::new));
            } catch(ArithmeticException e) {
                votesForEach.add(0);
                votesForEach.add(0);
                votesForEach.add(0);
                votesForEach.add(0);
            }

        // last element is only used a number of views for each poll
        votesForEach.add(totalVotes);

        em.getTransaction().commit();
        em.clear();
        em.close();
        emf.close();
        return votesForEach;
    }

    public static void insertCommentsIntoDataBase(long questionId, String username, String value) {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("comment_hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Comment comment = new Comment();
        comment.setValue(value);
        comment.setQuestion_id(questionId);
        comment.setUsername(username);
        session.save(comment);
        t.commit();
        factory.close();
        session.close();
    }

    public static List<Comment> getCommentFromDataBaseByQuestionId(EntityManagerFactory emf, long questionId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);
        Root<Comment> getComment = cq.from(Comment.class);
        
        cq.where(cb.equal(getComment.get("question_id"), questionId));
        CriteriaQuery<Comment> select = cq.select(getComment);
        TypedQuery<Comment> tq = em.createQuery(select);
        List<Comment> list = tq.getResultList();

        em.getTransaction().commit();
        em.close();
        emf.close();
        return list;
    }

    public static List<Question> searchQuestion(EntityManagerFactory emf, String search) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        AbstractQuery<Question> cq = cb.createQuery(Question.class);
        Root<Question> getQuestion = cq.from(Question.class);

        String[] searchWords = search.split("\\s+");


        List<Question> finalList = new ArrayList<Question>();

        for(String searchWord : searchWords) {            
            String searchResult = "%"+searchWord+"%";
            cq.where(cb.like(getQuestion.get("question"), searchResult));
            CriteriaQuery<Question> select = ((CriteriaQuery<Question>) cq).select(getQuestion);
            TypedQuery<Question> tq = em.createQuery(select);
            List<Question> list = tq.getResultList();
            finalList.addAll(list);
        }

        return finalList;
    }

}