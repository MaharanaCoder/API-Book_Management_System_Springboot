package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.modal.books;
import com.example.demo.repositary.books_repositary;

@Controller
public class books_controller {
	
	@Autowired
	private books_repositary books_repositary;
	
	@GetMapping("/getbooks")
	public ResponseEntity<?> getBooks() {
		List<books> books = books_repositary.findAll();
		
		if(books.isEmpty()) {
			return new ResponseEntity<>("Books Not Found",HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(books,HttpStatus.OK);
		}
	}
	
	@GetMapping("/getbooks/{id}")
	public ResponseEntity<?> getBooksById(@PathVariable Long id) {
		Optional<books> books_data = books_repositary.findById(id);
		
		if(books_data.isEmpty()) {
			return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(books_data,HttpStatus.OK);
		}
	}
	
	@PostMapping("/insertbook")
	public ResponseEntity<?> insertBook(@RequestBody books books) {
		books saveBooks = books_repositary.save(books);
		return new ResponseEntity<>("Successfully Added Book ",HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable Long id) {
		books_repositary.deleteById(id);
		return new ResponseEntity<>("Book successfully deleted",HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody books books) {
	Optional<books> selected_book = books_repositary.findById(id);
	if(selected_book.isPresent()) {
		books book = selected_book.get();
		book.setTitle(books.getTitle());
		book.setAuthor(books.getAuthor());
		book.setPublisher(books.getPublisher());
		book.setPublish_date(books.getPublish_date());
		
		books updateBooks = books_repositary.save(book);
		return new ResponseEntity<>(updateBooks,HttpStatus.OK);
	}else {
		return new ResponseEntity<>("User Not Found",HttpStatus.NOT_FOUND);
	}
		
	}
}
