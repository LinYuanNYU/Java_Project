package com.example.javaproject.AI;

public class Play {
    public static void main(String[] args) {
        String gameP = "Easy";
        if(args.length == 1){
            gameP = args[0];
        }

//        Injector injector = Guice.createInjector(new TexasModule(LogLevel.ALL, GamePropertiesParameter.fromString(gameP)));
//
//        PokerController pokerController = injector.getInstance(PokerController.class);
//        pokerController.play();
    }


}
