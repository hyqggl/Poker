import java.util.Comparator;

/*
 * Poker cards, rules for comparing
 */
public class Card/* implements Comparator<Card>*/{
	public String color;
	public String point;
	public Card(String color, String point) {
		this.color = color;
		this.point = point;
	}
	public static Comparator<Card> comparator = new Comparator<Card>() {
	public int compare(Card o1, Card o2) {
		PokerSet tempPoker = new PokerSet();
		if (o1 == null && o2 == null) return 0;
		int comp = tempPoker.points.indexOf(o1.point) - tempPoker.points.indexOf(o2.point);
		if (comp == 0) {
			comp = -tempPoker.colors.indexOf(o1.color) + tempPoker.colors.indexOf(o2.color);
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
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (point == null) {
			if (other.point != null)
				return false;
		} else if (!point.equals(other.point))
			return false;
		return true;
	}

	public String getColorPoint() {
		return color + point;
	}
}
