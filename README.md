# CEN 4072 - Software Testing Final Project
## LinkedIn Automated Test Suite

**Course:** CEN 4072 – Software Testing (Spring 2026)  
**System Under Test:** [LinkedIn.com](https://www.linkedin.com)  
**Tools:** Java 17, Selenium WebDriver, TestNG, Maven, Firefox (GeckoDriver), IntelliJ IDEA, GitHub

---

## Quick Start

### Prerequisites
- **Java 17+** installed → verify: `java --version`
- **Maven** installed → verify: `mvn --version`
- **Firefox** browser installed (latest)
- **IntelliJ IDEA** (Community or Ultimate)
- **Git** installed

### Setup in IntelliJ
1. Clone the repository:
   ```bash
   git clone https://github.com/YOUR_TEAM/CEN4072-Final-Project.git
   ```
2. Open IntelliJ → **File → Open** → select the project folder
3. IntelliJ will auto-detect the `pom.xml` and import Maven dependencies
4. Wait for indexing to complete

### Configure Credentials
1. Copy the template:
   ```bash
   cp src/test/resources/config.properties.example src/test/resources/config.properties
   ```
2. Edit `src/test/resources/config.properties` with your LinkedIn credentials:
   ```properties
   linkedin.email=your_real_email@example.com
   linkedin.password=your_real_password
   ```
3. **NEVER commit this file** — it's in `.gitignore`

### Run All Tests
```bash
mvn clean test
```

### Run from IntelliJ
- Right-click `src/test/resources/testng.xml` → **Run**
- Or right-click any individual test class → **Run**

---

## Project Structure

```
CEN4072-Final-Project/
├── pom.xml                              # Maven config (dependencies + plugins)
├── .gitignore                           # Protects credentials & build files
├── README.md                            # This file
├── src/
│   └── test/
│       ├── java/
│       │   ├── utils/
│       │   │   ├── BaseTest.java        # Firefox WebDriver setup/teardown + login helper
│       │   │   └── ConfigReader.java    # Reads config.properties
│       │   └── tests/
│       │       ├── HomepageTest.java    # [Member 1] Public homepage tests
│       │       ├── LoginPageTest.java   # [Member 1] Login page UI + validation
│       │       ├── LoginAuthTest.java   # [Member 1] Successful authentication tests
│       │       ├── SearchTest.java      # [Member 2] Search functionality
│       │       ├── NavigationTest.java  # [Member 2] Nav bar link tests
│       │       ├── JobsPageTest.java    # [Member 2] Jobs page features
│       │       ├── ProfilePageTest.java # [Member 3] User profile page tests
│       │       ├── MessagingTest.java   # [Member 3] Messaging page tests
│       │       └── NetworkTest.java     # [Member 3] My Network page tests
│       └── resources/
│           ├── testng.xml               # TestNG suite config (parallel execution)
│           └── config.properties        # LinkedIn credentials (git-ignored)
```

---

## Team Member Assignments

| Member     | Test Classes                                        | Tests |
|------------|-----------------------------------------------------|-------|
| Member 1   | HomepageTest, LoginPageTest, LoginAuthTest           | 15    |
| Member 2   | SearchTest, NavigationTest, JobsPageTest             | 15    |
| Member 3   | ProfilePageTest, MessagingTest, NetworkTest          | 15    |
| **Shared** | BaseTest, ConfigReader, testng.xml, pom.xml, README  | —     |

**Total: 9 test classes × 5 methods each = 45 test methods**

---

## Parallel Testing

The `testng.xml` is configured with:
```xml
<suite name="LinkedIn Test Suite" parallel="classes" thread-count="3">
```

- **parallel="classes"** — each test class runs in its own thread
- **thread-count="3"** — up to 3 Firefox instances run simultaneously
- **ThreadLocal<WebDriver>** in `BaseTest.java` ensures thread safety

---

## GitHub Workflow for Team Collaboration

```bash
# Each member works on their own branch:
git checkout -b member1-homepage-login
# ... write/edit your test classes ...
git add .
git commit -m "Add HomepageTest and LoginPageTest"
git push origin member1-homepage-login

# Then create a Pull Request on GitHub to merge into main
```

### Merging Strategy
1. One member creates the repo and pushes the base project (utils + pom.xml + testng.xml)
2. Each member creates a feature branch for their test classes
3. Open Pull Requests → review → merge into `main`
4. After all merges: `mvn clean test` on `main` to verify everything works

---

## Important Notes

- **LinkedIn may show CAPTCHAs or security challenges** during automated login.
  If this happens, log in manually once on Firefox to "trust" your device, then re-run tests.
- **Dynamic elements:** LinkedIn uses React and dynamic class names.
  The XPaths in tests use multiple fallback selectors to handle this.
- **Screen recording:** Required per project guidelines — use OBS Studio or similar
  to record your test execution as a backup.
- **TestNG Reports:** After running tests, find HTML reports in `target/surefire-reports/`
  and `test-output/` folders.

---

## How to Generate TestNG Reports

After running `mvn clean test`, reports are auto-generated:
- **Surefire Report:** `target/surefire-reports/index.html`
- **TestNG HTML Report:** `test-output/index.html`

Include screenshots of these in your final presentation and report.
