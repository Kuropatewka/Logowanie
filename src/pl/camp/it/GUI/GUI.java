package pl.camp.it.GUI;

import org.apache.commons.codec.digest.DigestUtils;
import pl.camp.it.database.Repository;
import pl.camp.it.model.Sex;
import pl.camp.it.model.User;

import java.util.ConcurrentModificationException;
import java.util.Scanner;

public class GUI {
    private static final Scanner scanner = new Scanner(System.in);

    public static void showMainMenu() {
        System.out.println("1. Zaloguj");
        System.out.println("2. Zarejestruj");
        System.out.println("3. Exit");

        switch (scanner.nextLine()) {
            case "1":
                showLogin();
                showMainMenu();
                break;
            case "2":
                showRegisterMenu();
                showMainMenu();
                break;
            case "3":
                System.exit(0);
                break;
            default:
                System.out.println("Nieprawidłowy wybór");
                showMainMenu();
                break;
        }
    }

    private static void showLogin() {
        System.out.println("Wpisz login:");
        String login = scanner.nextLine();
        System.out.println("Wpisz hasło:");
        String pass = scanner.nextLine();

        for (User user : Repository.getRepository().getUsers()) {
            if (user.getLogin().equals(login)) {
                //TODO
                //haszowanie i sprawdzanie hasła
                String hashedPassword = DigestUtils.md5Hex(pass);
                if (hashedPassword.equals(user.getPass())) {
                    System.out.println("OK");
                } else {
                    System.out.println("Niepoprawne dane");
                }
                return;
            }
        }
        System.out.println("Niepoprawne dane");
    }

    private static void showRegisterMenu() {

        System.out.println("Podaj login:");
        String login = scanner.nextLine();

        if (checkUserExist(login)) {
            System.out.println("Login zajęty");
            showRegisterMenu();
            return;
        } else {
            System.out.println("Login ok");
        }


        System.out.println("Podaj hasło:");
        String pass = scanner.nextLine();

        Sex sex = readSex();

        String hashedPassword = DigestUtils.md5Hex(pass);

        Repository.getRepository().getUsers().add(new User(
                Repository.getRepository().getUsers().size()+1,
                login,
                hashedPassword,
                sex));

        System.out.println("Rejestracja zakończona powodzeniem");

    }

    private static Sex readSex() {
        System.out.println("Podaj płeć (M/W/O): ");
        switch (scanner.nextLine().toUpperCase()) {
            case "M":
                return Sex.MAN;
            case "W":
                return Sex.WOMEN;
            case "O":
                return Sex.OTHER;
            default:
                readSex();
                break;

        }
        return null;
    }

    private static boolean checkUserExist(String login) {
        for (User user : Repository.getRepository().getUsers()) {
            if(user.getLogin().equals(login)) {
                return true;
            }
        }

        return false;
    }
}
