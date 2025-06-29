package ga.overfullstack.lombok.custombuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Message {
	private String sender;
	private String recipient;
	private String text;
	private List<?> files;

	public static class MessageBuilder {

		public <FileT> MessageBuilder files(FileT... files) {
			this.files = List.of(files);
			return this;
		}

		public <FileT> MessageBuilder file(FileT file) {
			if (this.files == null) {
				this.files = new ArrayList<FileT>();
			}
			((Collection<? super FileT>) this.files).add(file);
			return this;
		}

		public <FileT> MessageBuilder files(Collection<FileT> files) {
			if (this.files == null) {
				this.files = new ArrayList<FileT>();
			}
			((Collection<? super FileT>) this.files).addAll(files);
			return this;
		}

		public MessageBuilder clearFiles() {
			if (this.files != null) {
				this.files.clear();
			}
			return this;
		}
	}
}
