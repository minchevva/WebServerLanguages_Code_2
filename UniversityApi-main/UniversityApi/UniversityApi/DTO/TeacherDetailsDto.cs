using UniversityApi.Models;

namespace UniversityApi.DTO;

public class TeacherDetailsDto : Teacher
{
    public List<Course> Courses { get; set; }

    public TeacherDetailsDto()
    {
    }
    public TeacherDetailsDto(Teacher teacher)
    {
        TeacherId = teacher.TeacherId;
        FirstName = teacher.FirstName;
        LastName = teacher.LastName;
        HireDate = teacher.HireDate;
    }
}
