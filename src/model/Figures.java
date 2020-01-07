package model;

public enum Figures {
    ONES ("jedynki", "suma wyrzuconych jedynek", "suma wyrzuconych jedynek", 5,1, 1), //0
    TWOS ("dwójki", "suma wyrzuconych dwójek", "suma wyrzuconych dwójek", 10,2, 2), //1
    THREES ("trójki", "suma wyrzuconych trójek", "suma wyrzuconych trójek", 15,3, 3), //2
    FOURS ("czwórki", "suma wyrzuconych czwórek", "suma wyrzuconych czwórek", 20,4, 4), //3
    FIVES ("piątki", "suma wyrzuconych piąttek", "suma wyrzuconych piąttek", 25,5, 6), //4
    SIXES("szóstki", "suma wyrzuconych szóstek", "suma wyrzuconych szóstek", 30,6,7), //5
    THREE_SAME ("3 jednakowe", "trzy takie same", "suma wszystkich kości", 30,7, 8),// 6
    FOUR_SAME ("4 jednakowe", "cztery takie same", "suma wszystkich kości", 30,8, 9), //7
    FULL ("full", "trójka i para", "25", 30,9, 10), //8
    STREET_SMALL ("mały strit", "cztery kości z oczkami po kolei", "30", 30, 10, 11), //9
    STREET_BIG("duży strit", "pięć kości z oczkami po kolei", "40", 40, 11,12), //10
    GENERAL ("5 jednakowych", "pięć takich samych", "50", 50,12,13), //11
    CHANCE ("szansa", "dowolny układ", "suma wszystkich kości", 30,13, 5); //12

    private String myName;
    private String description;
    private String points;
    private int numberOfFigure;
    private int maxPoints;


    Figures(String myName, String description, String points, int maxPoints, int numberOfFigure, int value) {
        this.myName = myName;
        this.description = description;
        this.points = points;
        this.maxPoints = maxPoints;
        this.numberOfFigure = numberOfFigure;
        int value1 = value;
    }

    public String getMyName() {
        return myName;
    }

    public int getNumberOfFigure() {
        return numberOfFigure;
    }

    public int getMaxPoints() {
        return maxPoints;
    }
}
