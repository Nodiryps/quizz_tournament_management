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

    public  CareTaker careTaker;
    public Question question;
    public String response;

    public MementoBuilding(Question q, String r,CareTaker caretaker) {
        super(q);// cree une copie question
        question = q;
        response = r;
        careTaker=caretaker; 
       caretaker.keepMemento(createMemento());
    }

    @Override
    public void undo() {
        System.out.println("Undo");
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

    private MementoImpl createMemento() {
        return new MementoImpl(question, response);
    }

    public void setMemento(Memento m) {
        System.out.println("Buildoing setMemento"+m);
        MementoImpl mi = (MementoImpl) m;
        this.question = mi.question;
        this.response = mi.response;
        System.out.println(" Building -question "+this.question + " et reponse "+this.response);
    }
    
    
}
