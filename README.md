# Big Data Project 1 - Java Spring Boot & R (GraalVM)

### 🧩 General Flow

```
MongoDB → Java App (R with Polyglot / GraalVM) → Web Server
```

### 🚀 Operation

1. **CSV data** is imported into MongoDB (each group uses only one column, e.g., `Col-1`).
2. **Java application** reads one data point (`double`) from MongoDB every second.
3. **R function** uses this data to generate a **graph (SVG format)**.
4. The graph automatically **updates every second** in the browser.
5. Updates stop after 100 data points.

---

## 📊 Graph Features

* **Library:** `lattice`
* **Function:** `xyplot()`
* **X-Axis:** Indexes `[0-99]`
* **Y-Axis:** `double` values read from MongoDB
* **Line type:** `type = "l"`
* **Color:** `saddlebrown`
* **Grid:** Enabled (`grid = TRUE`)

### 📷 Example Image

<img width="911" height="801" alt="image" src="https://github.com/user-attachments/assets/6ed8c073-b527-453e-8306-8c1a13304477" />

---

## ⚙️ Prerequisites

| Requirement        | Description                                                              |
| ------------------ | ------------------------------------------------------------------------ |
| **Java (GraalVM)** | JDK 17+ (with R support)                                                 |
| **Maven**          | 3.6 or above                                                             |
| **MongoDB**        | Local server (`localhost:27017`), database: `swe307`, collection: `pro1` |
| **R**              | Integrated with GraalVM                                                  |
| **Others**         | Lombok, Spring Data MongoDB (loaded via Maven dependencies)              |

---

## 🧱 Installation Steps

### 1️⃣ Download Project Code

```bash
git clone <https://github.com/melisaaydin/Big-Data-Class-GraalVM-R-SpringBoot-Project1.git>
cd <BigDataProject1-R-GraalVM-SpringBoot>
```

Ensure `pom.xml` exists in the root directory.

---

### 2️⃣ Configure Application

Edit `src/main/resources/application.properties`:

```properties
spring.application.name=bigdata
spring.data.mongodb.uri=mongodb://localhost:27017/swe307
spring.data.mongodb.database=swe307
```

Place the `plot.R` file in `src/main/resources` folder.

---

### 3️⃣ Build Project

```bash
mvn clean install
```

---

### 4️⃣ Run Application

```bash
mvn spring-boot:run
```

> The application will run on `http://localhost:8080`.

---

### 5️⃣ Test

Open in browser:

```
http://localhost:8080/plot
```

The graph updates with one new data point every second. Updates stop after 100 points.

---

## ⚠️ Possible Issues & Solutions

| Issue                   | Possible Cause                | Solution                                    |
| ----------------------- | ----------------------------- | ------------------------------------------- |
| `R component not found` | R is not installed in GraalVM | Run `gu install R`                          |
| `Data not reading`      | MongoDB is offline            | Ensure `mongod` service is running          |
| `Graph not showing`     | No SVG or refresh support     | Refresh browser, enable `Refresh: 1` header |
