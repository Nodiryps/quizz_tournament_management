/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author 2208sptheodorou
 */
//CREATEUR
public class MementoBuilding extends UndoableQuestion {

    private CareTaker careTaker;
    public  Question question;
    public  String response;

    public MementoBuilding(Question q, String r) {
        super(q);
        careTaker = new CareTaker(createMemento(q, r));
    }

    @Override
    public void undo() {
        setMemento(careTaker.getMemento());
    }

    private static class MementoImpl implements Memento {

        Question question;
        String response;

        public MementoImpl(Question q, String r) {
            question = new Question(q);
            response = r;
        }

        public Question getQuestion() {
            return question;
        }

        public String getResponse() {
            return response;
        }

        @Override
        public String toString() {
            return "MementoImpl{" + "response=" + response + '}';
        }

      
        
    }

    public Memento createMemento(Question q, String r) {
        return new MementoImpl(q, r);
    }

    public void setMemento(Memento m) {
        System.out.println(m);
        MementoImpl mi = (MementoImpl) m;
        this.question = mi.question;
        this.response = mi.response;
    }
}
