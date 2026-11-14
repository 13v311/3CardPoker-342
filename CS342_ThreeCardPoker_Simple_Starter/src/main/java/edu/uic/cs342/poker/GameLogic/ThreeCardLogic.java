package edu.uic.cs342.poker.GameLogic;

import edu.uic.cs342.poker.cards.Card;

import java.util.List;

public class ThreeCardLogic {

    public enum HandRank{
        HIGH_CARD,
        PAIR,
        FLUSH,
        STRAIGHT,
        THREE_OF_KIND,
        STRAIGHT_FLUSH
    }

    public static HandRank evalHand(List<Card> hand){
        if(hand.isEmpty() || hand.size() < 3){
            throw new IllegalArgumentException("Poker hand needs 3 cards");
        }

        boolean flush = isFlush(hand);
        boolean straight = isStraight(hand);
        boolean threeOfKind = isThreeOfKind(hand);
        boolean pair = isPair(hand);

        if(flush && straight){
            return HandRank.STRAIGHT_FLUSH;
        }

        if(threeOfKind){
            return HandRank.THREE_OF_KIND;
        }

        if(straight){
            return HandRank.STRAIGHT;
        }

        if(flush){
            return HandRank.FLUSH;
        }

        if(pair){
            return HandRank.PAIR;
        }

        return HandRank.HIGH_CARD;
    }

    public static int compareHands(List<Card> dealer, List<Card> player){
        HandRank dealerRank = evalHand(dealer);
        HandRank playerRank = evalHand(player);

        int i = Integer.compare(playerRank.ordinal(), dealerRank.ordinal());

        if(i != 0){
            return i;
        }

        return tiebreaker(dealer, player);

    }

    private static int tiebreaker(List<Card> dealer, List<Card> player){
        dealer.sort((a,b) -> Integer.compare(b.getValue(), a.getValue()));
        player.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        for(int i = 0; i < 3; i++){
            int dealerRank = dealer.get(i).getValue();
            int playerRank = player.get(i).getValue();

            if(playerRank > dealerRank){
                return 1;
            }

            if(playerRank < dealerRank){
                return -1;
            }


        }
        return 0;
    }

    public static int evalPPWinnings(List<Card> hand, int bet){
        HandRank playerRank = evalHand(hand);

        switch (playerRank) {
            case STRAIGHT_FLUSH -> {
                return bet*40;
            }
            case THREE_OF_KIND -> {
                return bet*30;
            }
            case STRAIGHT -> {
                return bet*6;
            }
            case FLUSH -> {
                return bet*3;
            }
            default -> {
                return bet;
            }
        }
    }


    private static boolean isThreeOfKind(List<Card> hand){
        return hand.get(0).rank() == hand.get(1).rank()
                && hand.get(1).rank() == hand.get(2).rank();
    }

    private static boolean isStraight(List<Card> hand){
        int[] ranks = hand.stream().mapToInt(Card::getValue).sorted().toArray();
        return ranks[0] + 1 == ranks[1] && ranks[1] + 1 == ranks[2];
    }

    private static boolean isFlush(List<Card> hand){
        return hand.get(0).suit() == hand.get(1).suit()
                && hand.get(1).suit() == hand.get(2).suit();
    }

    private static boolean isPair(List<Card> hand){
        return hand.get(0).rank() == hand.get(1).rank()
                || hand.get(0).rank() == hand.get(2).rank()
                || hand.get(1).rank() == hand.get(2).rank();
    }


}
