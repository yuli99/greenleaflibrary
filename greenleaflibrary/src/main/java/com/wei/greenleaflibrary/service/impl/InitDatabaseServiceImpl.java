package com.wei.greenleaflibrary.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.wei.greenleaflibrary.dao.BookDao;
import com.wei.greenleaflibrary.dao.PublisherDao;
import com.wei.greenleaflibrary.dao.RoleDao;
import com.wei.greenleaflibrary.dao.UserDao;
import com.wei.greenleaflibrary.domain.Book;
import com.wei.greenleaflibrary.domain.Publisher;
import com.wei.greenleaflibrary.domain.Role;
import com.wei.greenleaflibrary.domain.User;
import com.wei.greenleaflibrary.service.InitDatabaseService;


public class InitDatabaseServiceImpl implements InitDatabaseService {

	private UserDao userDao;
	private RoleDao roleDao;
	private BookDao bookDao;
	private PublisherDao publisherDao;
	
	@Override
	@PostConstruct
	public void init() {
		if(roleDao.findRoleByName("ROLE_ADMIN") == null) {
			
			// add roles
			Role roleAdmin = new Role("ROLE_ADMIN");
			Role roleUser = new Role("ROLE_USER");
			
			try {
				roleDao.save(roleAdmin);
				roleDao.save(roleUser);
			} catch (Exception exc) {
				exc.printStackTrace();
			}
			
			// add users
			User user = new User();
			user.setUserName("admin");
			user.setCardNumber("100100100");
			user.setFullName("Adminstrator");
			user.setEmail("admin@email.com");
			user.setPassword((new BCryptPasswordEncoder()).encode("admin"));
			
			Date date = new GregorianCalendar(1985, Calendar.FEBRUARY, 10).getTime();
			user.setDateOfBirth(date);
						
			List<Role> roles = new ArrayList<>();
			roles.add(roleAdmin);
			roles.add(roleUser);
			user.setRoles(roles);
			
			try {
				userDao.save(user);
			} catch (Exception exc) {
				exc.printStackTrace();
			}
			
		}
		
		if (bookDao.findAll() == null || bookDao.findAll().size() < 1) {
			// add publishers
			Publisher mcGraw = new Publisher("McGraw-Hill Professional Publishing");
			Publisher pearson = new Publisher("Pearson");
			Publisher addison = new Publisher("Addison-Wesley");
			Publisher cengage = new Publisher("Cengage Learning");
			Publisher manning = new Publisher("Manning Publications Company");
			Publisher prentice = new Publisher("Prentice Hall");
			Publisher jones = new Publisher("Jones & Bartlett Learning");
			Publisher apress = new Publisher("Apress");
			
			try {
				publisherDao.save(mcGraw);
				publisherDao.save(pearson);
				publisherDao.save(addison);
				publisherDao.save(cengage);
				publisherDao.save(manning);
				publisherDao.save(prentice);
				publisherDao.save(jones);
				publisherDao.save(apress);
			} catch (Exception exc) {
				exc.printStackTrace();
			}		
					
			// add books			
			Book book1 = new Book("9780071809252", "Java, Sixth Edition: A Beginner's Guide", 
					"6th edition", "9780071809252.jpg");
			String authors1 = "Herbert Schildt";
			String description1 = "Bestselling programming author Herb Schildt begins with the basics, such as how to create, "
					+ "compile, and run a Java program. He then moves on to the keywords, syntax, and constructs that form the"
					+ " core of the Java language. This Oracle Press resource also covers some of Java's more advanced features, "
					+ "including multithreaded programming, generics, and Swing.";
			book1.setAuthors(authors1);
			book1.setDescription(description1);
			book1.setPublisher(mcGraw);
			
			Book book2 = new Book("9780321541406", "Data Structures and Problem Solving Using Java ", 
					"4th edition", "9780321541406.jpg");
			String authors2 = "Mark A. Weiss";
			String description2 = "This book takes a practical and unique approach to data structures that separates interface "
					+ "from implementation. It is suitable for the second or third programming course.  It provides a practical "
					+ "introduction to data structures with an emphasis on abstract thinking and problem solving, as well as the "
					+ "use of Java.";
			book2.setAuthors(authors2);
			book2.setDescription(description2);
			book2.setPublisher(pearson);
			
			Book book3 = new Book("9780321356680", "Effective Java", "2nd edition", "9780321356680.jpg");
			String authors3 ="Joshua Bloch";
			String description3 = "This book brings together seventy-eight indispensable programmer’s rules of thumb: working, "
					+ "best-practice solutions for the programming challenges you encounter every day. This highly anticipated new "
					+ "edition of the classic, Jolt Award-winning work has been thoroughly updated to cover Java SE 5 and Java SE 6 "
					+ "features introduced since the first edition.";
			book3.setAuthors(authors3);
			book3.setDescription(description3);
			book3.setPublisher(addison);
		
			Book book4 = new Book("9780133594959", "Java Software Solutions ", "8th edition", "9780133594959.jpg");
			String authors4 = "John Lewis, William Loftus";
			String description4 = "Java Software Solutions teaches a foundation of programming techniques to foster well-designed "
					+ "object-oriented software. Heralded for its integration of small and large realistic examples, this worldwide "
					+ "best-selling text emphasizes building solid problem-solving and design skills to write high-quality programs.";
			book4.setAuthors(authors4);
			book4.setDescription(description4);
			book4.setPublisher(pearson);
			
			Book book5 = new Book("9780072254549", "Java Demystified", "1st edition", "9780072254549.jpg");
			String authors5 = "Jim Keogh";
			String description5 = "Learn Java with this fun and painless self-teaching guide. Easy-to-understand, step-by-step instruction "
					+ "explains the most commonly used programming language for designing dynamic Web pages. Numerous examples, quizzes at "
					+ "the end of each chapter, and a final exam solidify the knowledge you have learned.";
			book5.setAuthors(authors5);
			book5.setDescription(description5);
			book5.setPublisher(mcGraw);
			
			Book book6 = new Book("9781285856919", "Java Programming", "8th edition", "9781285856919.jpg");
			String authors6 = "Joyce Farrell";
			String description6 = "With this book, even first-time programmers can quickly develop useful programs while learning the basic "
					+ "principles of structured and object-oriented programming. The text incorporates the latest version of Java with a "
					+ "reader-friendly presentation and meaningful real-world exercises that highlight new Java strengths.";
			book6.setAuthors(authors6);
			book6.setDescription(description6);
			book6.setPublisher(cengage);
			
			Book book7 = new Book("9780134285436", "Java Foundations: Introduction to Program Design and Data Structures", 
					"4th edition", "9780134285436.jpg");
			String authors7 = "Joe Chase, Peter DePasquale, John Lewis";
			String description7 = "Java Foundations is a comprehensive textbook for introductory programming sequences. Inspired by the success of "
					+ "their highly successful text, Java Software Solutions, authors Lewis, DePasquale and Chase build a solid framework for "
					+ "lasting comprehension.";
			book7.setAuthors(authors7);
			book7.setDescription(description7);
			book7.setPublisher(pearson);
			
			Book book8 = new Book("9781617290459", "Java Persistence with Hibernate", "2nd edition", "9781617290459.jpg");
			String authors8 = "Christian Bauer, Gavin King, Gary Gregory";
			String description8 = "Java Persistence with Hibernate explores Hibernate by developing an application that ties together hundreds of "
					+ "individual examples. You'll immediately dig into the rich programming model of Hibernate 3.2 and Java Persistence, working "
					+ "through queries, fetching strategies, caching, transactions, conversations, and more.";
			book8.setAuthors(authors8);
			book8.setDescription(description8);
			book8.setPublisher(manning);
			
			Book book9 = new Book("9780132121361", "Android for Programmers: An App-Driven Approach", "1st edition", "9780132121361.jpg");
			String authors9 = "Paul J. Deitel";
			String description9 = "This book gives you everything you’ll need to start developing great Android apps quickly and getting them published "
					+ "on Android Market. The book uses an app-driven approach—each new technology is discussed in the context of 16 fully tested Android "
					+ "apps, complete with syntax coloring, code walkthroughs and sample outputs.";
			book9.setAuthors(authors9);
			book9.setDescription(description9);
			book9.setPublisher(prentice);
			
			Book book10 = new Book("9780133544619", "Modern Database Management", "12th edition", "9780133544619.jpg");
			String authors10 = "Jeffrey A. Hoffer, V. Ramesh, Heikki Topi";
			String description10 = "Focusing on what leading database practitioners say are the most important aspects to database development, Modern "
					+ "Database Management presents sound pedagogy, and topics that are critical for the practical success of database professionals.";
			book10.setAuthors(authors10);
			book10.setDescription(description10);
			book10.setPublisher(pearson);			
			
			Book book11 = new Book("9780133544626", "Database Concepts", "7th edition", "9780133544626.jpg");
			String authors11 = "David Kroenke, David Auer";
			String description11 = "Here practical help for understanding, creating, and managing small databases—from two of the world’s leading database "
					+ "authorities. This book gives undergraduate database management students and business professionals alike a firm understanding of "
					+ "the concepts behind the software, using Access 2013 to illustrate the concepts and techniques.";
			book11.setAuthors(authors11);
			book11.setDescription(description11);
			book11.setPublisher(prentice);
			
			Book book12 = new Book("9780133760064", "Computer Science: An Overview", "12th edition", "9780133760064.jpg");
			String authors12 = "Glenn Brookshear";
			String description12 = "This book  uses broad coverage and clear exposition to present a complete picture of the dynamic computer science field. "
					+ "Accessible to students from all backgrounds, Glenn Brookshear and Dennis Brylow encourage the development of a practical, realistic "
					+ "understanding of the field.";
			book12.setAuthors(authors12);
			book12.setDescription(description12);
			book12.setPublisher(pearson);		
			
			Book book13 = new Book("9780201558029", "Concrete Mathematics: A Foundation for Computer Science", "2nd edition", "9780201558029.jpg");
			String authors13 = "Ronald L. Graham";
			String description13 = "This book introduces the mathematics that supports advanced computer programming and the analysis of algorithms. The primary "
					+ "aim is to provide a solid and relevant base of mathematical skills - the skills needed to solve complex problems, to evaluate horrendous "
					+ "sums, and to discover subtle patterns in data.";
			book13.setAuthors(authors13);
			book13.setDescription(description13);
			book13.setPublisher(addison);
			
			Book book14 = new Book("9781449672843", "Computer Science Illuminated", "5th edition", "9781449672843.jpg");
			String authors14 = "Nell Dale";
			String description14 = "The book provides a thorough overview of computing systems to prepare computer science majors for further study, yet remains "
					+ "accessible and engaging for non-majors looking for a comprehensive introduction to computing.";
			book14.setAuthors(authors14);
			book14.setDescription(description14);
			book14.setPublisher(jones);
			
			Book book15 = new Book("9781590595848", "Expert Spring MVC and Web Flow", "1st edition", "9781590595848.jpg");
			String authors15 = "Colin Yates";
			String description15 = "This book  provides in-depth coverage of Spring MVC and Spring Web Flow, two highly customizable and powerful web frameworks "
					+ "brought to you by the developers and community of the Spring Framework.";
			book15.setAuthors(authors15);
			book15.setDescription(description15);
			book15.setPublisher(apress);
			
			try {
				bookDao.save(book1);
				bookDao.save(book2);
				bookDao.save(book3);
				bookDao.save(book4);
				bookDao.save(book5);
				bookDao.save(book6);
				bookDao.save(book7);
				bookDao.save(book8);
				bookDao.save(book9);
				bookDao.save(book10);
				bookDao.save(book11);
				bookDao.save(book12);
				bookDao.save(book13);
				bookDao.save(book14);
				bookDao.save(book15);
			} catch (Exception exc) {
				exc.printStackTrace();
			}			
		}
	}
	
	// getters and setters
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public BookDao getBookDao() {
		return bookDao;
	}

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public PublisherDao getPublisherDao() {
		return publisherDao;
	}

	public void setPublisherDao(PublisherDao publisherDao) {
		this.publisherDao = publisherDao;
	}

}
