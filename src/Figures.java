public enum Figures {
    ONES ("jedynki", "suma wyrzuconych jedynek", "suma wyrzuconych jedynek",1),
    TWOS ("dwójki", "suma wyrzuconych dwójek", "suma wyrzuconych dwójek",2),
    THREES ("trójki", "suma wyrzuconych trójek", "suma wyrzuconych trójek",3),
    FOURS ("czwórki", "suma wyrzuconych czwórek", "suma wyrzuconych czwórek",4),
    FIVES ("piątki", "suma wyrzuconych piąttek", "suma wyrzuconych piąttek",5),
    SIXES("szóstki", "suma wyrzuconych szóstek", "suma wyrzuconych szóstek",6),
    THREE_SAME ("3 jednakowe", "trzy takie same", "suma wszystkich kości",7),
    FOUR_SAME ("4 jednakowe", "cztery takie same", "suma wszystkich kości",8),
    FULL ("full", "trójka i para", "25",9),
    STREET_SMALL ("mały strit", "cztery kości z oczkami po kolei", "30", 10),
    STREET_BIG("duży strit", "pięć kości z oczkami po kolei", "40", 11),
    GENERAL ("5 jednakowych", "pięć takich samych", "50",12),
    CHANCE ("szansa", "dowolny układ", "suma wszystkich kości",13);

    private String myName;
    private int numberOfFigure;


    Figures(String myName, String description, String points, int numberOfFigure) {
        this.myName = myName;
        this.numberOfFigure = numberOfFigure;
    }

    public String getMyName() {
        return myName;
    }

    public int getNumberOfFigure() {
        return numberOfFigure;
    }
}
