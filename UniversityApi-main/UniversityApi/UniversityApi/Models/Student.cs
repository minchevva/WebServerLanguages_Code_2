namespace UniversityApi.Models;

public class Student
{
    public int StudentId { get; set; }
    public string FirstName { get; set; }
    public string LastName { get; set; }
    public DateTime? Birthdate { get; set; }
    public int GradeLevel { get; set; }
}
