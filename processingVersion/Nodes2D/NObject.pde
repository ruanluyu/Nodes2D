
 int nameId = 0;// Local this id.
 int id = 0;// Global all ids' number.
 void setNameId(int id) {
    nameId = id;
  }
   int getNameId() {
    return nameId;
  }
class NObject {
   
  protected String CLASSNAME = "NObject";
  protected String title;
  protected int thisId = 0;// Global this id.
  protected boolean idAddable = true;
  PVector minSize = new PVector(200,100);
  PVector size = new PVector(200,100);
  PVector position = new PVector(width/2,height/2);
  
  void render(){}
  
  ///////////////

  public NObject() {
    initializeObject();
    if (idAddable) {
      thisId = id++;
      title = CLASSNAME + " " + nameId;
      nameId++;
    } else {
      thisId = -1;
      title = CLASSNAME;
    }
  }

  protected void initializeObject() {
    CLASSNAME = "NObject";
    idAddable = true;
  }

  //////////////

  

  

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public String getClassName() {
    return CLASSNAME;
  }

  public void println(Object num) {
    println(num + "");
  }

  public void println(boolean num) {
    println(num + "");
  }

  public void println(double num) {
    println(num + "");
  }

  public void println(int num) {
    println(num + "");
  }

  public void println(String message) {
    println(this.title + " ( class : " + CLASSNAME + " $global_id " + thisId + " ) : \n" + message + "\n");

  }

  public int getId() {
    return thisId;
  }

  public NObject clone() {
    println("Warning : there is no clone method in this class.");
    return null;
  }
}