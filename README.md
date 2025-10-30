Esquema actual de la orgaización del proyecto.

**Este readme debe ser editado una vez esté completo


Desafio2/


├── src/

│   └── poo/

│       └── desafio2/

│           ├── gui/

│           │   ├── Main.java

│           │   ├── VentanaAgregarMaterial.java

│           │   ├── VentanaModificarMaterial.java

│           │   ├── VentanaListarMateriales.java     (FALTA - Persona 3)

│           │   └── VentanaBuscarMaterial.java       (FALTA - Persona 3)

│           ├── dao/

│           │   ├── DBConnection.java

│           │   ├── MaterialDAO.java

│           │   ├── MaterialDAOImpl.java

│           │   ├── LibroDAO.java

│           │   ├── RevistaDAO.java

│           │   ├── CDDAO.java

│           │   └── DVDDAO.java

│           ├── model/

│           │   ├── Material.java

│           │   ├── Libro.java

│           │   ├── Revista.java

│           │   ├── CD.java

│           │   ├── DVD.java

│           │   └── TipoMaterial.java

│           ├── util/

│           │   └── LoggerManager.java

│           └── exceptions/

│               ├── DatabaseException.java

│               └── ValidationException.java

├── src/main/resources/

│   └── database.properties

├── pom.xml

└── log4j.properties

