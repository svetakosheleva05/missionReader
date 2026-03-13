/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader;
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
    
    public Manager(PathValidator pathValidator, ParserFactory parserFactory, MissionStorage missionStorage) {
        this.pathValidator = pathValidator;
        this.parserFactory = parserFactory;
        this.missionStorage = missionStorage;
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        System.out.println("Начинаем работу");
        while (true) {
            System.out.print("Введите путь к файлу (или 'выход' для выхода): ");
            String path = scanner.nextLine().trim();
            if (path.trim().equalsIgnoreCase("выход") || path.trim().equalsIgnoreCase("exit")) {
                System.out.println("Работа закончена");
                break;
            }   
            processSingleMission(path);
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
        mission.printReport();     
    }
}