namespace UniversityApi.DTO;

public class CourseDetailsDto
{
    public int CourseId { get; set; }
    public int EnrollmentId { get; set; }
    public string CourseName { get; set; }
    public int Credits { get; set; }
    public float? Grade { get; set; }
}
