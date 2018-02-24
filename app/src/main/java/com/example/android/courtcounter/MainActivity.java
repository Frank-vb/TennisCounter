package com.example.android.courtcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

 //This app handles scoring for a Tennis match
 //A match is won, by winning 2 sets
 //A set is won, by winning 6 games, with a difference of 2 against the other player
 //  If both players have won 6 games, tie-Break scoring is applied
 //  First player with 7 points (with a difference of 2) wins the tie-break.
 //  Tie-break points are showed within same textfield as regular points
 //A game is won, by getting 4 points
 //   1 point equals 15, 2 points equals 30, 3 points equals 40, 4 points equals "Deuce"

    String[] pointsString = {"0", "15", "30", "40", "ADV"};
    int pointsTeamA = 0;
    int pointsTeamB = 0;
    int gamesTeamA = 0;
    int gamesTeamB = 0;
    int setsTeamA = 0;
    int setsTeamB = 0;
    boolean tieBreakeScoring = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void plusOneA (View view) {
        // run method gamePointA to increase points for team A.
        // Then update textviews with current score
        gamePointA();
        displaysPointsTeamA(pointsTeamA);
    }

    private void gamePointA() {
    //Adds points, and calls methods if there are enough points for gaining a game or having a tie-break

        //If both teams have 6 setpoints, tieBreak sooring is used and method tieBreak is called
        // in this case, the normal point system is not applied
        if (gamesTeamA == 6 && gamesTeamB == 6) {

            tieBreakeScoring = true;
            boolean tieBrakeA = tieBreak(true);

            //When boolean tieBreak is true, this means teamA has won the tieBreak and ..
            // therefore team A gains one setpoint (via method matchPoint)
            if (tieBrakeA){
                tieBreakeScoring = false;
                matchPointA();
            }
        } else {
        // else statement is applied if tie-break is not applicable.
        // Check if team A has enough points for a game.
            // If false, add 1 point
            // If true, set points to 0 and run setPoint method to add setPoints


            //If team A has not enough points for a game and team B doesn't have Advantage, add 1 point.
            if ( (pointsTeamA < 3 && pointsTeamB != 4) | (pointsTeamA == 3 && pointsTeamB == 3)) {
                pointsTeamA += 1;
            }
            //If both teams reach 4 points (Adv), scoring is set back to 3 (40)
            else if (pointsTeamA == 3 && pointsTeamB == 4) {
                pointsTeamB -= 1;
            }
            //If both of above are not applicable, team A gains a gamepoint (call method setPointA)
            else {
                pointsTeamA = 0;
                pointsTeamB = 0;
                setPointA();
            }
        }

        displaysPointsTeamA(pointsTeamA);
        displaysPointsTeamB(pointsTeamB);

    }

    private void setPointA(){
    // Check if team A has enough points for a set (6 points with difference of 2)
        // If false, add 1 set point
        // If true, run matchPointA method
        if(gamesTeamA > 4 && gamesTeamB +1 < gamesTeamA) {

            gamesTeamA = 0;
            gamesTeamB = 0;
            matchPointA();

        }
        //Check if both players have 6 games and calls method tieBreak for alternative scoring system
            else if(gamesTeamA == 6 && gamesTeamB == 6) {
            boolean tieBreakA = tieBreak(true);

                //When tieBrakeA returns as true, this means team A has won the tieBreak
                if (tieBreakA){
                    matchPointA();
                }
        }
                else {

                    pointsTeamA = 0;
                    pointsTeamB = 0;
                    gamesTeamA += 1;
            }

        displaysPointsTeamA(pointsTeamA);
        displaysPointsTeamB(pointsTeamB);
        displaysGamesTeamA(gamesTeamA);
    }

    private boolean tieBreak(boolean tieBrakeScorerA) {

        boolean tieBrakeA;
        boolean tieBrakeB;

        //Add one point to tieBreakScore Team A
        if (tieBrakeScorerA) {
            pointsTeamA += 1;

        //If Team A has 7 points with difference of 2, return to setPointA, to add setpointScore
            if (pointsTeamA > 6 && (pointsTeamB + 1) < pointsTeamA) {

                pointsTeamA = 0;
                pointsTeamB = 0;
                return (tieBrakeA = true);
            }

        } else {
        //Add one point to tieBreakScore Team B
            pointsTeamB += 1;

        //If Team B has 7 points with difference of 2, return to setPointB, to add setpointScore
            if (pointsTeamB > 6 && (pointsTeamA + 1) < pointsTeamB) {
                pointsTeamA = 0;
                pointsTeamB = 0;
                return (tieBrakeB = true);
            }
        }
        return(tieBrakeA = false);
    }

    private void matchPointA(){

        //increase number of sets, and reset number of games. Display values on textviews
        setsTeamA += 1;
        gamesTeamA = 0;
        gamesTeamB = 0;
        displaysGamesTeamA(gamesTeamA);
        displaysGamesTeamB(gamesTeamB);
        displaysSetsTeamA(setsTeamA);

        //If team has 2 sets, game is won.
        if (setsTeamA == 2){
            String winnerName = "Team A Wins!!";
            Winner(winnerName);
        }
    }

    public void plusOneB (View view) {
        // run method gamePointB to increase points for team B.
        // Then update textviews with current score
        gamePointB();
        displaysPointsTeamB(pointsTeamB);
    }

    private void gamePointB() {
        //Adds points, and calls methods if there are enough points for gaining a game or having a tie-break

        //If both teams have 6 setpoints, tieBreak sooring is used and method tieBreak is called
        // in this case, the normal point system is not applied
        if (gamesTeamB == 6 && gamesTeamA == 6) {

            tieBreakeScoring = true;
            boolean tieBrakeB = tieBreak(false);

            //When boolean tieBreak is true, this means teamA has won the tieBreak and ..
            // ..therefore team B gains one setpoint
            if (tieBrakeB){
                tieBreakeScoring = false;
                matchPointB();
            }
        } else {
            // else statement is applied if tie-break is not applicable.
            // Check if team B has enough points for a game.
            // If false, add 1 point
            // If true, set points to 0 and run setPoint method to add setPoints

            //If team B has not enough points for a game and team A doesn't have Deuce, add 1 point.
            if ((pointsTeamB < 3 && pointsTeamA != 4) | (pointsTeamB == 3 && pointsTeamA == 3))  {
                pointsTeamB += 1;
            }
            //If both teams reach 4 points (Deuce), scoring is set back to 3 (40)
            else if (pointsTeamB == 3 && pointsTeamA == 4) {
                pointsTeamA -= 1;
            }
            //If both of above are not applicable, team B gains a gamepoint (call method setPointA)
            else {
                pointsTeamB = 0;
                pointsTeamA = 0;
                setPointB();
            }
        }

        displaysPointsTeamA(pointsTeamA);
        displaysPointsTeamB(pointsTeamB);

    }

    private void setPointB(){
        // Check if team B has enough points for a set (6 points with difference of 2)
        // If false, add 1 set point
        // If true, run matchPointB method
        if(gamesTeamB > 4 && gamesTeamA < gamesTeamB) {

            gamesTeamB = 0;
            gamesTeamA = 0;
            matchPointB();

        }
        //Check if both players have 6 games and calls method tieBreak for alternative scoring system
        else if(gamesTeamB == 6 && gamesTeamA == 6) {

            boolean tieBrakeB = true;
            tieBreak(tieBrakeB);

            if (tieBrakeB){
                matchPointB();
            }
        }
        else {
            gamesTeamB += 1;
        }

        displaysGamesTeamB(gamesTeamB);
    }

    private void matchPointB(){
        setsTeamB += 1;
        gamesTeamA = 0;
        gamesTeamB = 0;
        displaysGamesTeamA(gamesTeamA);
        displaysGamesTeamB(gamesTeamB);
        displaysSetsTeamB(setsTeamB);

        if (setsTeamB == 2){

            String winnerName = "Team B Wins!!";
            Winner(winnerName);
        }
    }

    private void displaysPointsTeamA(int score) {

        String textScore = String.valueOf(score);
        if (!tieBreakeScoring){
            textScore = pointsString[score];
            //convert points 1/2/3/4 to 15/30/40/D
        }

        //If tie-break scoring is applied, points are showed as regular numbers
        TextView scoreView = (TextView) findViewById(R.id.team_a_score);
        scoreView.setText(textScore);
    }

    private void displaysGamesTeamA(int score) {
        TextView scoreView2 = (TextView) findViewById(R.id.team_a_score2);
        scoreView2.setText(String.valueOf(score));
    }

    private void displaysSetsTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score3);
        scoreView.setText(String.valueOf(score));
    }

    private void displaysPointsTeamB(int score) {

        String textScore = String.valueOf(score);
        if (!tieBreakeScoring){
            textScore = pointsString[score]; }

        TextView scoreView = (TextView) findViewById(R.id.team_b_score);
        scoreView.setText(textScore);
    }

    private void displaysGamesTeamB(int score) {
        TextView scoreView2 = (TextView) findViewById(R.id.team_b_score2);
        scoreView2.setText(String.valueOf(score));
    }

    private void displaysSetsTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score3);
        scoreView.setText(String.valueOf(score));
    }

    private void Winner(String Winner){
    //Makes the textview winnerName visible and displays the text.
    //also the buttons are set invisible, so user cant press them


        TextView winnerMessage = findViewById(R.id.winnerName);
        winnerMessage.setText(Winner);
        winnerMessage.setVisibility(winnerMessage.VISIBLE);

        Button pointTeamA = findViewById(R.id.pointTeamA);
        pointTeamA.setVisibility(pointTeamA.INVISIBLE);

        Button pointTeamB = findViewById(R.id.pointTeamB);
        pointTeamB.setVisibility(pointTeamB.INVISIBLE);
    }

    public void Reset (View view) {
        //Set all score variables to zero
        pointsTeamB = 0;
        pointsTeamA = 0;
        gamesTeamA = 0;
        gamesTeamB = 0;
        setsTeamA = 0;
        setsTeamB = 0;

        //Update all textviews with value 0.
        displaysPointsTeamA(pointsTeamA);
        displaysGamesTeamA(gamesTeamA);
        displaysSetsTeamA(setsTeamA);
        displaysPointsTeamB(pointsTeamB);
        displaysGamesTeamB(gamesTeamB);
        displaysSetsTeamB(setsTeamB);

        //Set winnerTextvield as invisible
        TextView winnerMessage = findViewById(R.id.winnerName);
        winnerMessage.setVisibility(View.INVISIBLE);

        //Make scoring buttons invisible
        Button pointTeamA = findViewById(R.id.pointTeamA);
        pointTeamA.setVisibility(View.VISIBLE);

        Button pointTeamB = findViewById(R.id.pointTeamB);
        pointTeamB.setVisibility(View.VISIBLE);
    }

}
