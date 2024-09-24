namespace UniversityApi.Models;

public class Enrollment
{
    public int EnrollmentId { get; set; }
    public int StudentId { get; set; }
    public int CourseId { get; set; }
    public int TeacherId { get; set; }
    public DateTime? EnrollmentDate { get; set; }
}
