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
    public Question question;
    public String response;

    public MementoBuilding(Question q, String r,CareTaker c) {
        super(q);// cree une copie question
        question = q;
        response = r;
        careTaker=c;
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

    public MementoImpl createMemento() {
        return new MementoImpl(question, response);
    }

    public void setMemento(Memento m) {
        MementoImpl mi = (MementoImpl) m;
        this.question = mi.question;
        this.response = mi.response;
    }
    
    
}
