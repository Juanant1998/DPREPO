package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.NoteRepository;
import domain.Note;


@Service
@Transactional
public class NoteService {
	
	@Autowired
	private NoteRepository noteRepository;
	
	public Note create(){
		return new Note();	
	}
	public Collection<Note> findAll(){
		return noteRepository.findAll();
	}
	
	public Note findOne(int noteId){
		return noteRepository.findOne(noteId);
	}
	
	public Note save(Note note){
		return noteRepository.save(note);
	}
	
	public void delete(Note note){
		noteRepository.delete(note);
	}

}
