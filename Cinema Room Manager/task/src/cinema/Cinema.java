package cinema;

import java.util.Scanner;

public class Cinema {

    static int numberOfRows;
    static int numberOfColumns;
    static int numberOfPurchaseTickets = 0;
    static int totalIncome = 0;
    static boolean[][] seats;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        numberOfRows = scan.nextInt();

        System.out.println("Enter the number of seats in each row:");
        numberOfColumns = scan.nextInt();

        seats = new boolean[numberOfRows][numberOfColumns];

        // Initialize all seats as available
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                seats[i][j] = true;
            }
        }

        showMenu(scan);
    }

    private static void showMenu(Scanner scan) {
        while (true) {
            System.out.println("""
                    1. Show the seats
                    2. Buy a ticket
                    3. Statistics
                    0. Exit
                    """);

            int choice = scan.nextInt();
            switch (choice) {
                case 1 -> showTheSeats();
                case 2 -> buyATicket(scan);
                case 3 -> showStatistics();
                case 0 -> {
                    System.out.println("Thanks for using the cinema ticket booking system. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void showStatistics() {
        int numberOfSeats = numberOfRows * numberOfColumns;
        double percentage = (double) numberOfPurchaseTickets / numberOfSeats * 100;

        System.out.println("Number of purchased tickets: " + numberOfPurchaseTickets);
        System.out.println("Percentage: " + String.format("%.2f", percentage) + "%");
        System.out.println("Current income: $" + totalIncome);
        System.out.println("Total income: $" + getTotalIncome());
    }

    private static int getTotalIncome() {
        int numberOfSeats = numberOfRows * numberOfColumns;
        if (numberOfSeats <= 60) {
            return numberOfSeats * 10;
        } else {
            int frontRows = numberOfRows / 2;
            int backRows = numberOfRows - frontRows;
            return (frontRows * numberOfColumns * 10) + (backRows * numberOfColumns * 8);
        }
    }

    private static void buyATicket(Scanner scan) {
        int rowNumber;
        int columnNumber;

        while (true) {
            System.out.println("Enter a row number: ");
            rowNumber = scan.nextInt();


            System.out.println("Enter a seat number in that row:");
            columnNumber = scan.nextInt();

            if (isValidSeat(rowNumber, columnNumber)) {
                if (isSeatBooked(rowNumber, columnNumber)) {
                    System.out.println("That ticket has already been purchased. Please choose another seat.");
                } else {
                    int ticketPrice = getTicketPrice(rowNumber);
                    System.out.println("Ticket price: $" + ticketPrice);

                    bookSeat(rowNumber, columnNumber);
                    showTheSeats();
                    break;
                }
            } else {
                System.out.println("Wrong input");
            }
        }
    }

    private static boolean isValidSeat(int rowNumber, int columnNumber) {
        return rowNumber >= 1 && rowNumber <= numberOfRows && columnNumber >= 1 && columnNumber <= numberOfColumns;
    }

    private static boolean isSeatBooked(int rowNumber, int columnNumber) {
        return !seats[rowNumber - 1][columnNumber - 1];
    }

    private static int getTicketPrice(int rowNumber) {
        int numberOfSeats = numberOfRows * numberOfColumns;
        int frontRows = numberOfRows / 2;

        if (numberOfSeats <= 60 || rowNumber <= frontRows) {
            return 10;
        } else {
            return 8;
        }
    }

    private static void bookSeat(int rowNumber, int columnNumber) {
        seats[rowNumber - 1][columnNumber - 1] = false;
        numberOfPurchaseTickets++;
        totalIncome += getTicketPrice(rowNumber);
    }

    private static void showTheSeats() {
        System.out.println("Cinema:");

        System.out.print("  ");
        for (int i = 1; i <= numberOfColumns; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 1; i <= numberOfRows; i++) {
            System.out.print(i + " ");
            for (int j = 1; j <= numberOfColumns; j++) {
                if (seats[i - 1][j - 1]) {
                    System.out.print("S ");
                } else {
                    System.out.print("B ");
                }
            }
            System.out.println();
        }
    }
}
