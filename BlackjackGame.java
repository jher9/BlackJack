import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
        String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public Card drawCard() {
        Random random = new Random();
        int index = random.nextInt(cards.size());
        return cards.remove(index);
    }
}

class Card {
    private String rank;
    private String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}

class Player {
    private List<Card> hand;

    public Player() {
        hand = new ArrayList<>();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public List<Card> getHand() {
        return hand;
    }

    public int getHandValue() {
        int value = 0;
        int numAces = 0;

        for (Card card : hand) {
            if (card.getRank().equals("Ace")) {
                value += 11;
                numAces++;
            } else if (card.getRank().equals("King") || card.getRank().equals("Queen") || card.getRank().equals("Jack")) {
                value += 10;
            } else {
                value += Integer.parseInt(card.getRank());
            }
        }

        while (value > 21 && numAces > 0) {
            value -= 10;
            numAces--;
        }

        return value;
    }

    public boolean isBust() {
        return getHandValue() > 21;
    }

    public Card getFaceUpCard() {
        if (hand.size() > 0) {
            return hand.get(0);
        } else {
            return null;
        }
    }
}

public class BlackjackGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Deck deck = new Deck();
        Player player = new Player();
        Player dealer = new Player();

        // Deal initial cards
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());

        System.out.println("Your hand: " + player.getHand());
        System.out.println("Dealer's face-up card: " + dealer.getFaceUpCard());

        // Player's turn
        while (true) {
            System.out.println("Do you want to hit or stand? (h/s)");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("h")) {
                player.addCard(deck.drawCard());
                System.out.println("Your hand: " + player.getHand());
                if (player.isBust()) {
                    System.out.println("Bust! You lose.");
                    break;
                }
            } else if (choice.equalsIgnoreCase("s")) {
                break; // Player stands
            }
        }

        // Dealer's turn
        while (!dealer.isBust() && dealer.getHandValue() < 17) {
            dealer.addCard(deck.drawCard());
        }
        System.out.println("Dealer's hand: " + dealer.getHand());

        // Determine the winner
        if (player.isBust() || (!dealer.isBust() && dealer.getHandValue() >= player.getHandValue())) {
            System.out.println("Dealer wins!");
        } else {
            System.out.println("You win!");
        }
    }
}