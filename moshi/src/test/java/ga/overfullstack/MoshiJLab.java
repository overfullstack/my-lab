package ga.overfullstack;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.ToJson;
import ga.overfullstack.models.BlackjackHand;
import ga.overfullstack.models.Card;
import ga.overfullstack.models.Suit;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MoshiJLab {

	@Test
	@DisplayName("Card adapter")
	void cardAdapter() throws IOException {
		var json =
				"{\n"
						+ "  \"hidden_card\": \"6S\",\n"
						+ "  \"visible_cards\": [\n"
						+ "    \"4C\",\n"
						+ "    \"AH\"\n"
						+ "  ]\n"
						+ "}\n";

		var moshi = new Moshi.Builder().add(new CardAdapter()).build();
		var jsonAdapter = moshi.adapter(BlackjackHand.class);

		BlackjackHand blackjackHand = jsonAdapter.fromJson(json);
		System.out.println(blackjackHand);
	}

	private static final class CardAdapter {
		@ToJson
		String toJson(Card card) {
			return card.rank + card.suit.name().substring(0, 1);
		}

		@FromJson
		Card fromJson(String card) {
			if (card.length() != 2) {
				throw new JsonDataException("Unknown card: " + card);
			}

			char rank = card.charAt(0);
			switch (card.charAt(1)) {
				case 'C':
					return new Card(rank, Suit.CLUBS);
				case 'D':
					return new Card(rank, Suit.DIAMONDS);
				case 'H':
					return new Card(rank, Suit.HEARTS);
				case 'S':
					return new Card(rank, Suit.SPADES);
				default:
					throw new JsonDataException("unknown suit: " + card);
			}
		}
	}
}
