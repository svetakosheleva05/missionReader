/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader;
import com.mycompany.missionreader.parsers.MissionParser;
import com.mycompany.missionreader.reporters.MissionReportBuilder;
import com.mycompany.missionreader.reporters.MissionReportDirector;
import com.mycompany.missionreader.reporters.Report;
import com.mycompany.missionreader.reporters.ReportSection;
import com.mycompany.missionreader.reporters.TxtFormatter;
import java.io.File;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author svetl
 */

public class Manager {
    private final PathValidator pathValidator;
    private final ParserFactory parserFactory;
    private final MissionStorage missionStorage;
    private final Scanner scanner;
    Set<ReportSection> sections = Set.of(ReportSection.BASIC_INFO, 
            ReportSection.DAMAGE_COST, ReportSection.CIVILIAN, ReportSection.PARTICIPANTS, ReportSection.TARGET_CURSE, ReportSection.TECHNIQUES,
            ReportSection.ECONOMIC, ReportSection.ENEMY_ACTIVITY, ReportSection.ENVIRONMENT,
            ReportSection.TIMELINE);
    
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
            String path = scanner.nextLine().trim().replace("\"", "");
            if (path.trim().equalsIgnoreCase("выход") || path.trim().equalsIgnoreCase("exit")) {
                System.out.println("Работа закончена");
                break;
            }   else if (path.trim().equalsIgnoreCase("Список миссий")) {
                missionStorage.printAll();
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
        MissionReportDirector director = new MissionReportDirector(mission, sections, new MissionReportBuilder(mission));
        Report report = director.construct();
        System.out.println(new TxtFormatter().format(report));
    }
}