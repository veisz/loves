package hu.targetshooting;

import hu.targetshooting.controller.ShotService;
import hu.targetshooting.model.service.*;

import java.util.Scanner;

public class App {

    private final ShotService service;
    private final Console console;
    private final FileWriter writer;

    public App() {
        DataApi dataApi = new DataApi(new FileReader(), new DataParser());
        service = new ShotService(dataApi.getData("verseny.txt"));
        console = new Console(new Scanner(System.in));
        writer = new FileWriter("sorrend.txt");
    }

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        System.out.println("2. feladat");
        System.out.println("Az egymást követően többször találó versenyzők: "
                + service.getTwoSuccessfulShotIds());
        System.out.println("3. feladat");
        System.out.println("A legtöbb lövést leadó versenyző rajtszáma :"
                +service.getLongestShotSequenceId());
        System.out.print("5. Feladat: Adjon meg egy rajtszámmot: ");
        int id = console.readId();
        System.out.println("5a. Feladat: Célt érő lövések: "
        +service.getSuccessShotIndexesById(id));
        System.out.println("5c. Feladat: S leghosszabb hibátlan sorozat hossza: "
                +service.getLongestSuccessSequenceSizeById(id));
        System.out.println("5d. Feladat: A versenyző pontszáma: "
                +service.getScoreById(id));
        writer.write(service.getFinalResult());
    }
}
