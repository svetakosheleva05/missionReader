
package com.mycompany.missionreader;

/**
 *
 * @author svetl
 */
public class MissionReader {

    public static void main(String[] args) {
        ParserFactory parserFactory = new ParserFactory(new SorcererRegister(), new CurseRegister(), new MissionStorage(), new MissionBuilder());
        new Manager(new PathValidator(), parserFactory, new MissionStorage()).start();
    }
}
