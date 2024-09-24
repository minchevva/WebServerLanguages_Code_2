using UniversityApi.DTO;
using UniversityApi.Models;

namespace UniversityApi.Repositories.Interfaces;

public interface IDbContext
{
    Task<IEnumerable<Student>> GetAllStudents();
    Task<Student> GetStudentById(int id);
    Task<IEnumerable<CourseDetailsDto>> GetCoursesByStudentId(int studentId);
    Task<int> AddStudent(Student student);
    Task UpdateStudent(Student studentToUpdate);
    Task DeleteStudent(int id);
    Task<IEnumerable<Teacher>> GetAllTeachers();
    Task<Teacher> GetTeacherById(int id);
    Task<IEnumerable<Course>> GetCoursesByTeacherId(int teacherId);
    Task<int> AddTeacher(Teacher teacher);
    Task UpdateTeacher(Teacher teacherToUpdate);
    Task DeleteTeacher(int id);
    Task<IEnumerable<Course>> GetAllCourses();
    Task<int> AddCourse(Course course);
    Task<Course> GetCourseById(int id);
    Task DeleteCourse(int id);
    Task<int> AddEnrollment(Enrollment enrollment);
    Task<Enrollment> GetEnrollmentByStudentAndCourseId(int studentId, int courseId);
    Task DeleteEnrollment(int enrollmentId);
    Task<int> AddGrade(Grade grade);
    Task<Grade> GetGradeById(int id);
    Task DeleteGrade(int id); 
}
