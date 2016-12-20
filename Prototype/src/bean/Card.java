package bean;

import util.ColorAndPoint;

import java.util.Comparator;

/*
 * Poker cards, rules for comparing
 */
public class Card/* implements Comparator<bean.Card>*/{

	private String color;
	private int colorIdx;
	private String point;
	private int pointIdx;

	public Card(int colorIdx, int pointIdx) {
		this.colorIdx = colorIdx;
		this.pointIdx = pointIdx;
		this.color = ColorAndPoint.colors[colorIdx];
		this.point = ColorAndPoint.points[pointIdx];
	}


	/**
	 * Compare two cards
	 * 比较两张卡牌
	 */
	//点数优先
	public static Comparator<Card> comparatorByPoint = new Comparator<Card>() {
	public int compare(Card o1, Card o2) {
		if (o1 == null && o2 == null) return 0;
		int comp = o1.getPointIdx() - o2.getPointIdx();
		if (comp == 0) {
			comp = -o1.getColorIdx() + o2.getColorIdx();
		}
		return comp;
	}
	};

	//花色优先
	public static Comparator<Card> comparatorByColor = new Comparator<Card>() {
		public int compare(Card o1, Card o2) {
			if (o1 == null && o2 == null) return 0;
			int comp = - o1.getColorIdx() + o2.getColorIdx();
			if (comp == 0) {
				comp = o1.getPointIdx() - o2.getPointIdx();
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

	public int getColorIdx() {
		return colorIdx;
	}

	public void setColorIdx(int colorIdx) {
		this.colorIdx = colorIdx;
	}

	public int getPointIdx() {
		return pointIdx;
	}

	public void setPointIdx(int pointIdx) {
		this.pointIdx = pointIdx;
	}
}
