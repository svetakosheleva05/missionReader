/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader;
import com.mycompany.missionreader.parsers.MissionParser;
import com.mycompany.missionreader.reporters.FullReportStrategy;
import com.mycompany.missionreader.reporters.Report;
import com.mycompany.missionreader.reporters.ReportStrategiesContext;
import com.mycompany.missionreader.reporters.ReportStrategy;
import com.mycompany.missionreader.reporters.ShortReportStrategy;
import com.mycompany.missionreader.reporters.TxtFormatter;
import java.io.File;
import java.util.Scanner;

/**
 *
 * @author svetl
 */

public class Manager {
    private final PathValidator pathValidator;
    private final ParserFactory parserFactory;
    private final MissionStorage missionStorage;
    private final Scanner scanner;
    private ReportStrategiesContext reportStrategiesContext;
    
    public Manager(PathValidator pathValidator, ParserFactory parserFactory, MissionStorage missionStorage, ReportStrategiesContext reportStrategiesContext) {
        this.pathValidator = pathValidator;
        this.parserFactory = parserFactory;
        this.missionStorage = missionStorage;
        this.scanner = new Scanner(System.in);
        this.reportStrategiesContext = reportStrategiesContext;
    }
    
    public void start() {
        System.out.println("Начинаем работу");
        while (true) {
            System.out.print("\nВведите путь к файлу (или 'выход' для выхода): ");
            String input = scanner.nextLine().trim().replace("\"", "");
            
            if (input.equalsIgnoreCase("выход") || input.equalsIgnoreCase("exit")) {
                System.out.println("Работа закончена");
                break;
            } else if (input.equalsIgnoreCase("список миссий")) {
                missionStorage.printAll();
                continue;
            }
            
            processSingleMission(input);
        }
    }
    
    private void processSingleMission(String path) {
        File file;
        if (pathValidator.isPathValid(path)) {
            file = new File(path);
        } else {
            System.out.println("Путь к файлу неверный");
            return;
        }
        
        MissionParser parser = parserFactory.getParser(file);
        if (parser == null) {
            System.out.println("Формат не поддерживается");
            return;
        }
        
        Mission mission = parser.parse(file);
        if (mission == null) {
            System.out.println("Не удалось распарсить файл");
            return;
        }
        
        missionStorage.addMissionIfNorExists(mission);
        reportStrategiesContext.setStrategy(chooseReportTypeForMission());
        Report report = reportStrategiesContext.generateReport(mission);
        System.out.println("\n" + new TxtFormatter().format(report));
        System.out.println("----------------------------------------");
        
        askForAnotherReport(mission);
    }
    
    private ReportStrategy chooseReportTypeForMission() {
        System.out.println("\n=== Какой отчет показать? ===");
        System.out.println("1. Полный отчет (full)");
        System.out.println("2. Краткий отчет (short)");
        System.out.print("Ваш выбор: ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
            case "full":
                System.out.println("Показываем полный отчет\n");
                return new FullReportStrategy();
            case "2":
            case "short":
                System.out.println("Показываем краткий отчет\n");
                return new ShortReportStrategy();
            default:
                System.out.println("Неверный выбор, показываем полный отчет\n");
                return new FullReportStrategy();
        }
    }
    
    private void askForAnotherReport(Mission mission) {
        while (true) {
            System.out.print("\nПоказать другой отчет для этой же миссии? (да/нет): ");
            String answer = scanner.nextLine().trim().toLowerCase();
            
            if (answer.equals("да") || answer.equals("yes")) {
                reportStrategiesContext.setStrategy(chooseReportTypeForMission());
                Report report = reportStrategiesContext.generateReport(mission);
                System.out.println("\n" + new TxtFormatter().format(report));
                System.out.println("----------------------------------------");
            } else if (answer.equals("нет") || answer.equals("no") || answer.equals("н")) {
                System.out.println("Переходим к следующей миссии...");
                break;
            } else {
                System.out.println("Ответьте 'да' или 'нет'");
            }
        }
    }
}