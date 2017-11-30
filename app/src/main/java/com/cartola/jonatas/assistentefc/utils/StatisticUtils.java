package com.cartola.jonatas.assistentefc.utils;

import android.util.Log;

import com.cartola.jonatas.assistentefc.model.PlayerScore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Giovana on 29/10/2017.
 */

public class StatisticUtils {

    private static double simpleArithmeticAveragePlayers(double money, int players) {
        return (money / players);
    }

    public static List<PlayerScore> getBestPlayers(List<PlayerScore> players, double money, int zagQuant, int latQuant, int midQuant, int ataQuant, int posToSpendMore) {
        int golQuant = 1;
        int tecQuant = 1;
        double teamPrice = 0;
        int playersQuant = 12;
        int posToSpendMoreQuant = -1;

        switch (posToSpendMore) {
            case 1:
                posToSpendMoreQuant = golQuant;
                break;
            case 2:
                posToSpendMoreQuant = latQuant;
                break;
            case 3:
                posToSpendMoreQuant = zagQuant;
                break;
            case 4:
                posToSpendMoreQuant = midQuant;
                break;
            case 5:
                posToSpendMoreQuant = ataQuant;
                break;
            default:
                posToSpendMoreQuant = -1;
        }

        Collections.sort(players);

        List<PlayerScore> idPlayersList = new ArrayList<>();

        while(playersQuant > 0) {
            double moneyMedia = simpleArithmeticAveragePlayers(money, playersQuant);
            double posToSpendMoreMedia = moneyMedia * 1.20;
            double generalMedia = simpleArithmeticAveragePlayers((money - (posToSpendMoreMedia * posToSpendMoreQuant)), (playersQuant - ataQuant));
            for(PlayerScore player : players) {
                if (player.getStatus() == 7) {
                    if ((golQuant > 0 || zagQuant > 0 || latQuant > 0 || midQuant > 0 || ataQuant > 0 || tecQuant > 0)) {
                        // goleiro = 1
                        if (player.getPosition() == 1) {
                            if (golQuant > 0) {

                                double mediaToSpend = 0;
                                if(posToSpendMore == 1)
                                    mediaToSpend = posToSpendMoreMedia;
                                else
                                    mediaToSpend = generalMedia;

                                if (player.getPrice() <= mediaToSpend) {
                                    if(!idPlayersList.contains(player)) {
                                        Log.e("escalation", player.getNickName() + " " + player.getPrice() + " " + player.getPosition());
                                        idPlayersList.add(player);
                                        teamPrice += player.getPrice();
                                        playersQuant--;
                                        golQuant--;
                                        money -= player.getPrice();
                                        if(posToSpendMore == 1)
                                            posToSpendMoreQuant--;
                                        break;
                                    }
                                }
                            }
                        }

                        // lateral = 2
                        if (player.getPosition() == 2) {
                            if (latQuant > 0) {

                                double mediaToSpend = 0;
                                if(posToSpendMore == 2)
                                    mediaToSpend = posToSpendMoreMedia;
                                else
                                    mediaToSpend = generalMedia;

                                if (player.getPrice() <= mediaToSpend) {
                                    if(!idPlayersList.contains(player)) {
                                        Log.e("escalation", player.getNickName() + " " + player.getPrice() + " " + player.getPosition());
                                        idPlayersList.add(player);
                                        teamPrice += player.getPrice();
                                        playersQuant--;
                                        latQuant--;
                                        money -= player.getPrice();
                                        if(posToSpendMore == 2)
                                            posToSpendMoreQuant--;
                                        break;
                                    }
                                }
                            }
                        }
                        // zagueiro = 3
                        if (player.getPosition() == 3) {
                            if (zagQuant > 0) {

                                double mediaToSpend = 0;
                                if(posToSpendMore == 3)
                                    mediaToSpend = posToSpendMoreMedia;
                                else
                                    mediaToSpend = generalMedia;

                                if (player.getPrice() <= mediaToSpend) {
                                    if(!idPlayersList.contains(player)) {
                                        Log.e("escalation", player.getNickName() + " " + player.getPrice() + " " + player.getPosition());
                                        idPlayersList.add(player);
                                        teamPrice += player.getPrice();
                                        playersQuant--;
                                        zagQuant--;
                                        money -= player.getPrice();
                                        if(posToSpendMore == 3)
                                            posToSpendMoreQuant--;
                                        break;
                                    }
                                }
                            }
                        }

                        // meia = 4
                        if (player.getPosition() == 4) {
                            if (midQuant > 0) {
                                double mediaToSpend = 0;
                                if(posToSpendMore == 4)
                                    mediaToSpend = posToSpendMoreMedia;
                                else
                                    mediaToSpend = generalMedia;

                                if (player.getPrice() <= mediaToSpend) {
                                    if(!idPlayersList.contains(player)) {
                                        Log.e("escalation", player.getNickName() + " " + player.getPrice() + " " + player.getPosition());
                                        idPlayersList.add(player);
                                        teamPrice += player.getPrice();
                                        playersQuant--;
                                        midQuant--;
                                        money -= player.getPrice();
                                        if(posToSpendMore == 4)
                                            posToSpendMoreQuant--;
                                        break;
                                    }
                                }
                            }
                        }

                        // atacante = 5
                        if (player.getPosition() == 5) {
                            if (ataQuant > 0) {

                                double mediaToSpend = 0;
                                if(posToSpendMore == 5)
                                    mediaToSpend = posToSpendMoreMedia;
                                else
                                    mediaToSpend = generalMedia;
                                if (player.getPrice() <= mediaToSpend) {
                                    if(!idPlayersList.contains(player)) {
                                        Log.e("escalation", player.getNickName() + " " + player.getPrice() + " " + player.getPosition());
                                        idPlayersList.add(player);
                                        teamPrice += player.getPrice();
                                        playersQuant--;
                                        ataQuant--;
                                        money -= player.getPrice();
                                        if(posToSpendMore == 5)
                                            posToSpendMoreQuant--;
                                        break;
                                    }
                                }
                            }
                        }
                        // tecnico = 6
                        if (player.getPosition() == 6) {
                            if (tecQuant > 0) {
                                if (player.getPrice() <= generalMedia) {
                                    if(!idPlayersList.contains(player)) {
                                        Log.e("escalation", player.getNickName() + " " + player.getPrice() + " " + player.getPosition());
                                        idPlayersList.add(player);
                                        teamPrice += player.getPrice();
                                        playersQuant--;
                                        tecQuant--;
                                        money -= player.getPrice();
                                        break;
                                    }
                                }
                            }
                        }
                    } /* else {
                        break;
                    }*/
                }
            }
        }

        /*for(PlayerScore player : players) {
            if (player.getStatus() == 7) {
                double restOfMoney = money - teamPrice;

                // goleiro = 1
                if (player.getPosition() == 1) {
                    if (golQuant > 0) {
                        if (player.getPrice() <= restOfMoney) {
                            idPlayersList.add(player.getId());
                            golQuant--;
                        }
                    }
                }

                // lateral = 2
                if (player.getPosition() == 2) {
                    if (latQuant > 0) {
                        if (player.getPrice() <= restOfMoney) {
                            idPlayersList.add(player.getId());
                            latQuant--;
                        }
                    }
                }
                // zagueiro = 3
                if (player.getPosition() == 3) {
                    if (zagQuant > 0) {
                        if (player.getPrice() <= restOfMoney) {
                            idPlayersList.add(player.getId());
                            zagQuant--;
                        }
                    }
                }

                // meia = 4
                if (player.getPosition() == 4) {
                    if (midQuant > 0) {
                        if (player.getPrice() <= restOfMoney) {
                            idPlayersList.add(player.getId());
                            midQuant--;
                        }
                    }
                }

                // atacante = 5
                if (player.getPosition() == 5) {
                    if (ataQuant > 0) {
                        if (player.getPrice() <= ataMedia) {
                            idPlayersList.add(player.getId());
                            ataQuant--;
                        }
                    }
                }
                // tecnico = 6
                if (player.getPosition() == 6) {
                    if (tecQuant > 0) {
                        if (player.getPrice() <= restOfMoney) {
                            idPlayersList.add(player.getId());
                            tecQuant--;
                        }
                    }
                }
            }
        }
        */

        Log.e("RestOfMoney", money + "");
        return idPlayersList;
    }
}
