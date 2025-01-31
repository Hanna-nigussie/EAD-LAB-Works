document.getElementById("client-signup-form").addEventListener("submit", signupClient);

function signupClient(event) {
    event.preventDefault();

    const name = document.getElementById("name").value;
    const lastname = document.getElementById("lastname").value;
    const email = document.getElementById("email").value;
    const phone = document.getElementById("phone").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    // Check if passwords match
    if (password !== confirmPassword) {
        document.getElementById("signupMessage").innerText = "Passwords do not match!";
        return;
    }

    // Create the signup request object
    const signupRequestDTO = {
        name: name,
        lastname: lastname,
        email: email,
        phone: phone,
        password: password
    };

    // Send POST request with JSON body
    fetch("http://localhost:8080/client/sign-up", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",  // Ensure the server knows we are sending JSON
			"Accept": "application/json"
        },
        body: JSON.stringify(signupRequestDTO)
    })
    .then(response => {
        console.log("Response status:", response.status); // Check response status
        if (response.ok) {
            return response.json();  // Only parse as JSON if the status is OK (200-299)
        } else {
            throw new Error('Failed to sign up. Server responded with status ' + response.status);
        }
    })
    .then(data => {
        console.log("Response data:", data);  // Log the data to see what the server sent back
        if (data && data.id) {
            document.getElementById("signupMessage").innerText = "Signup successful!";
        } else {
            document.getElementById("signupMessage").innerText = "Signup failed. Try again!";
        }
    })
    .catch(error => {
        console.error("Error during signup:", error); // Detailed error log
        document.getElementById("signupMessage").innerText = "An error occurred!";
    });
}
