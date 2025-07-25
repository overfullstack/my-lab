/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ga.overfullstack.models;

public final class Card {
	public final char rank;
	public final Suit suit;

	public Card(char rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	@Override
	public String toString() {
		return String.format("%s%s", rank, suit);
	}
}
