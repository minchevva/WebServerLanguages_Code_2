namespace UniversityApi.DTO;

public class StudentDetailsDto
{
    public int StudentId { get; set; }
    public string FirstName { get; set; }
    public string LastName { get; set; }
    public int GradeLevel { get; set; }
    public List<CourseDetailsDto> Courses { get; set; }
    public int TotalCredits => Courses?.Where(c => c.Grade != null && c.Grade >= 3).Sum(c => c.Credits) ?? 0;
}
