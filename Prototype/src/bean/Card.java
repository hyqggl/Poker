package bean;

import util.PokerSetColorAndPoint;

import java.util.Comparator;

/*
 * Poker cards, rules for comparing
 */
public class Card/* implements Comparator<bean.Card>*/{

	private String color;
	private String point;

	public Card(String color, String point) {
		this.color = color;
		this.point = point;
	}


	/**
	 * Compare two cards
	 * 比较两张卡牌
	 */
	public static Comparator<Card> comparator = new Comparator<Card>() {
	public int compare(Card o1, Card o2) {
		if (o1 == null && o2 == null) return 0;
		int comp = PokerSetColorAndPoint.points.indexOf(o1.getPoint()) - PokerSetColorAndPoint.points.indexOf(o2.getPoint());
		if (comp == 0) {
			comp = -PokerSetColorAndPoint.colors.indexOf(o1.getColor()) + PokerSetColorAndPoint.colors.indexOf(o2.getColor());
		}
		return comp;
	}
	};
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Card))
			return false;
		Card other = (Card) obj;
		if (color == null) {
			if (other.getColor() != null)
				return false;
		} else if (!color.equals(other.getColor()))
			return false;
		if (point == null) {
			if (other.getPoint() != null)
				return false;
		} else if (!point.equals(other.getPoint()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return " [ " + color + " " + point + " ] ";
	}

	public String getColorPoint() {
		return this.color + " " + this.point;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}
}
