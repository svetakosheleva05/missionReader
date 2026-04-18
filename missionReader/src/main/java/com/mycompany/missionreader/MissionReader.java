
package com.mycompany.missionreader;

import com.mycompany.missionreader.reporters.FullReportStrategy;
import com.mycompany.missionreader.reporters.ReportStrategiesContext;

/**
 *
 * @author svetl
 */
public class MissionReader {

    public static void main(String[] args) {
        ParserFactory parserFactory = new ParserFactory(new SorcererRegister(), new CurseRegister(), new MissionStorage(), new MissionBuilder());
        new Manager(new PathValidator(), parserFactory, new MissionStorage(), new ReportStrategiesContext(new FullReportStrategy())).start();
    }
}
