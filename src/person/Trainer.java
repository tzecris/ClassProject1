package person;

import static classproject.ClassProject.printMenu;
import classproject.SyntheticValues;
import classproject.Validation;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Trainer extends Person {

    private String subject;

    ArrayList<Student> trainerList = new ArrayList<>();

    public Trainer(String firstName, String lastName, String subject) {
        super(firstName, lastName);
        this.subject = subject;

    }

    public Trainer() {
    }

    public static void addTrainerToList(Scanner sc, List<Trainer> listOfTrainers) {
        String input;
        System.out.println("1.Add new Trainer");
        System.out.println("2.Import from existing Trainer");
        input = sc.nextLine();
        while (!input.equalsIgnoreCase("1") && !input.equalsIgnoreCase("2")) {
            System.out.println("Give a valid answer:");
            input = sc.nextLine();
        }
        if (input.equals("2")) {

            if (!listOfTrainers.containsAll(SyntheticValues.syntheticTrainers())) {
                listOfTrainers.addAll(SyntheticValues.syntheticTrainers());
            }
            System.out.println(listOfTrainers);
            return;
        }
        Trainer t1 = new Trainer();

        System.out.println("Give first name:");
        input = sc.nextLine();
        while (!Validation.stringChecker(input, Validation.INVALID_CHARACTERS)) {
            System.out.println("Give valid name:");
            input = sc.nextLine();
        }
        t1.setFirstName(input);

        System.out.println("Give last name:");
        input = sc.nextLine();
        while (!Validation.stringChecker(input, Validation.INVALID_CHARACTERS)) {
            System.out.println("Give valid last name:");
            input = sc.nextLine();
        }
        t1.setLastName(input);

        System.out.println("Give teacher's subject name:");
        input = sc.nextLine();
        while (!Validation.stringChecker(input, Validation.INVALID_CHARACTERS)) {
            System.out.println("Give valid subject name:");
            input = sc.nextLine();
        }
        t1.setSubject(input);

        if (!listOfTrainers.contains(t1)) {
            listOfTrainers.add(t1);
        } else {
            System.out.println("Trainer already exist.");
            printMenu();
        }

    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ArrayList<Student> getTrainerList() {
        return trainerList;
    }

    public void setTrainerList(ArrayList<Student> trainerList) {
        this.trainerList = trainerList;
    }

    @Override
    public String toString() {
        return super.toString() + ", subject=" + subject + "." + "\n";
    }

}
