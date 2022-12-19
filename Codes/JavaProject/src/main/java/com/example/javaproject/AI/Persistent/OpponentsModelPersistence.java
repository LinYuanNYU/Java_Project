package com.example.javaproject.AI.Persistent;


import com.example.javaproject.AI.gameModel.opponentModel.*;
import com.example.javaproject.AI.Game;
import com.example.javaproject.AI.Player;
import com.example.javaproject.AI.gameProperties.GameProperties;
import com.example.javaproject.infra.Poker.Card;
import com.example.javaproject.AI.Evaluator.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

public class OpponentsModelPersistence {
    private static final String TABLE_OPPONENTS_MODEL = "Opponents";
    private final PersistenceManager persistenceManager;

    public OpponentsModelPersistence() {
        PersistenceManager persistenceManager = new PersistenceManager();
        this.persistenceManager = persistenceManager;
        init();
    }

    public static void persist(ContextAggregate contextAggregate) {
        try {
            String insert = "INSERT INTO " + TABLE_OPPONENTS_MODEL + " VALUES(?,?,?,?,?,?,?,?,?)";
            PersistenceManager persistenceManager = new PersistenceManager();
            PreparedStatement statement = persistenceManager.getConnection().prepareStatement(insert);
            ContextAction contextAction = contextAggregate.getContextAction();
            statement.setInt(1, contextAction.getPlayer().getNumber());
            statement.setString(2, contextAction.getBettingDecision().toString());
            statement.setString(3, contextAction.getBettingRoundName().toString());
            statement.setString(4, contextAction.getContextRaises().toString());
            statement.setString(5, contextAction.getContextPlayers().toString());
            statement.setString(6, contextAction.getContextPotOdds().toString());
            statement.setInt(7, contextAggregate.getNumberOfOccurrences());
            statement.setDouble(8, contextAggregate.getHandStrengthAverage());
            statement.setDouble(9, contextAggregate.getDeviation());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public  ModelResult retrieve(ContextAction contextAction) {
        String query = "SELECT * FROM " + TABLE_OPPONENTS_MODEL + " WHERE player = ? AND decision = ? AND " +
                "roundname = ? AND raises = ? AND playercount = ? AND potodds = ?";

        try {
            PreparedStatement statement = persistenceManager.getConnection().prepareStatement(query);
            statement.setInt(1, contextAction.getPlayer().getNumber());
            statement.setString(2, contextAction.getBettingDecision().toString());
            statement.setString(3, contextAction.getBettingRoundName().toString());
            statement.setString(4, contextAction.getContextRaises().toString());
            statement.setString(5, contextAction.getContextPlayers().toString());
            statement.setString(6, contextAction.getContextPotOdds().toString());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return new ModelResult(result.getDouble("handstrength"), result.getDouble("deviation"),
                        result.getInt("occurences"));
            } else {
                return new ModelResult(0, 0, 0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public  void print() {
        String query = "SELECT * FROM " + TABLE_OPPONENTS_MODEL + " ORDER BY player, decision, roundname, " +
                "raises, playercount, potodds";
        try {
            PreparedStatement statement = persistenceManager.getConnection().prepareStatement(query);
            ResultSet result = statement.executeQuery();

            DecimalFormat f = new DecimalFormat("##.0000");
            while (result.next()) {
                String handstrength = f.format(result.getDouble("handstrength"));
                String deviation = f.format(result.getDouble("deviation"));


            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public static void clear() {
        String query = "TRUNCATE TABLE " + TABLE_OPPONENTS_MODEL;
        try {
            PersistenceManager persistenceManager = new PersistenceManager();
            Statement statement = persistenceManager.getConnection().createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }

    }

    private static void init() {
        try {
            PersistenceManager persistenceManager = new PersistenceManager();
            Statement statement = persistenceManager.getConnection().createStatement();
            //statement.execute("DROP TABLE "+TABLE_OPPONENTS_MODEL);
            String query = "CREATE TABLE IF NOT EXISTS " + TABLE_OPPONENTS_MODEL + "(player integer," +
                    "decision VARCHAR_IGNORECASE,roundname VARCHAR_IGNORECASE, raises VARCHAR_IGNORECASE, " +
                    "playercount VARCHAR_IGNORECASE, potodds VARCHAR_IGNORECASE, occurences integer, " +
                    "handstrength double, deviation double)";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}