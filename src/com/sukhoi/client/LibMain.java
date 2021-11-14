package com.sukhoi.client;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.sukhoi.exceptions.InvalidOperationException;
import com.sukhoi.presentation.LibPresentation;
import com.sukhoi.presentation.LibPresentationImpl;

public class LibMain {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		LibPresentation libPresentation = new LibPresentationImpl();
		System.out.println(
				"***********************************************************************************************");

		System.out.println("Aloha! Welcome to UIT Library :)");

		System.out.println(
				"***********************************************************************************************");

		System.out.println("Login as admin/student?  (type admin/student)");
		String user = scanner.nextLine();

		if (user.equals("admin")) {

			System.out.println("Enter Your Password:");
			int chances = 3;
			String password = scanner.nextLine();
			while (chances > 0) {
				if (password.equals("sukhoi")) {
					while (true) {
						libPresentation.showMenuAdmin();
						System.out.println("Enter choice");
						try {
							int choice = scanner.nextInt();
							if (choice > 12 || choice < 1)
								throw new InvalidOperationException("Invalid operation requested! try again ");
							libPresentation.performMenuAdmin(choice);
						} catch (InputMismatchException e) {
							System.out.println("Hey hey! invalid input format detected--we asked for a valid number");
							scanner.next();
						} catch (InvalidOperationException e) {
							System.out.println(e.getMessage());
						}

					}
				} else {
					chances--;
					if (chances != 0)
						System.out.println(
								"Not the correct password!Try again " + chances + " left for giving correct password!");
					else
						System.out.println(
								"OOps ! seems you forgot your password-- have a cup of coffee and try again later");
					scanner.next();
				}
			}
		} else if (user.equals("student")) {

			while (true) {
				libPresentation.showMenuStudent();
				System.out.println("Enter choice");
				try {
					int choice = scanner.nextInt();
					if (choice > 2 || choice < 1)
						throw new InvalidOperationException("Invalid operation requested! try again ");
					libPresentation.performMenuStudent(choice);
				} catch (InputMismatchException e) {
					System.out.println("Hey hey! invalid input format detected--we asked for a valid number ");
					scanner.next();
				} catch (InvalidOperationException e) {
					System.out.println(e.getMessage());
				}

			}

		} else {
			System.out.println("Enter a valid user");
		}

	}

}
