package fr.asterox.NotesCentral.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoNoteException extends RuntimeException {

	public NoNoteException(String msg) {
		super(msg);
	}

}
