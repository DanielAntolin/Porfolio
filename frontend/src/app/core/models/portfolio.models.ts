export interface Profile {
  fullName: string;
  title: string;
  summary: string;
  focusAreas: string[];
}

export interface Experience {
  company: string;
  role: string;
  period: string;
  employmentType: string;
  highlights: string[];
  technologies: string[];
}

export interface Education {
  institution: string;
  program: string;
  period: string;
  status: string;
  description: string;
}

export interface SkillGroup {
  category: string;
  items: string[];
}

export interface Project {
  name: string;
  description: string;
  period: string;
  technologies: string[];
  url: string | null;
  primaryLanguage: string;
  stars: number;
}

export interface Language {
  name: string;
  level: string;
}

export interface SocialLink {
  label: string;
  url: string | null;
  username: string;
}

export interface Contact {
  email: string;
  phone: string;
  location: string;
  socialLinks: SocialLink[];
}

export interface Portfolio {
  profile: Profile;
  experience: Experience[];
  education: Education[];
  skills: SkillGroup[];
  projects: Project[];
  languages: Language[];
  contact: Contact;
}
