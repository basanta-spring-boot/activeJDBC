package com.basant.spring.active.jdbc.api;

import java.util.List;

import org.javalite.activejdbc.Base;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ActivejdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivejdbcApplication.class, args);
		Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/test", "root", "cisco");
		ActivejdbcApplication application = new ActivejdbcApplication();

		// insert record approach 1
		application.insert2(906, "Bikash", "Panigrahi");

		// insert record approach 2
		application.insert2(876, "Basant", "Hota");

		// fetch single record
		System.out.println(application.findPerson("Hota"));

		// fetch record using where clause
		System.out.println(application.findPersonByWhereClause("Hota"));

		// fetch ALl Records
		System.out.println(application.findAllPerson());

		// update record
		System.out.println(application.updatePerson("xyz", 876));

		// delete record
		System.out.println(application.deletePerson(876));

		// delete all records
		System.out.println(application.deletePersons());

		// get pagination data
		application.getPersons();
	}

	public void insert1(int id, String first_name, String last_name) {
		Person person = new Person();
		person.set("id", id);
		person.set("first_name", first_name);
		person.set("last_name", last_name);
		person.insert();
		System.out.println("Record inserted ...");
	}

	public void insert2(int id, String first_name, String last_name) {
		new Person().set("id", id, "first_name", first_name, "last_name", last_name).insert();
	}

	public Person findPerson(String last_name) {
		return Person.findFirst("last_name = ?", last_name);
	}

	public List<Person> findPersonByWhereClause(String last_name) {
		return Person.where("last_name = ?", last_name);
	}

	public void getPersons() {
		List<Person> people = Person.where("last_name = ?", "xyz").offset(0).limit(10).orderBy("id asc");
		System.out.println(people);
	}

	public List<Person> findAllPerson() {
		return Person.findAll();
	}

	public String updatePerson(String last_name, int id) {
		Person person = Person.findFirst("id = ?", id);
		person.set("last_name", last_name).save();
		return "record updated with id : " + id;
	}

	public String deletePerson(int id) {
		Person person = Person.findFirst("id = ?", id);
		person.delete();
		return "Record deleted ";
	}

	public String deletePersons() {
		int no = Person.deleteAll();
		return "Record deleted " + no;
	}
}
